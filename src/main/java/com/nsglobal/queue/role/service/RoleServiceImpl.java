package com.nsglobal.queue.role.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.role.dto.RoleRequestDto;
import com.nsglobal.queue.role.dto.RoleResponseDto;
import com.nsglobal.queue.role.entity.Permission;
import com.nsglobal.queue.role.entity.Role;
import com.nsglobal.queue.role.mapper.RoleMapper;
import com.nsglobal.queue.role.repository.PermissionRepository;
import com.nsglobal.queue.role.repository.RoleRepository;
import com.nsglobal.queue.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
	
	  private final RoleRepository roleRepository;

	    private final PermissionRepository permissionRepository;

	    private final RoleMapper roleMapper;
	    
	    private final UserRepository userRepo;
	    
	    private final AuditService audit;
	    
	    @Transactional(readOnly = true)
	    private Role getById(Long id,String subMsg) {
	    	Role existanceRole=roleRepository.findById(id)
					.orElseThrow(()->new RuntimeException("Role introuvable %s".formatted(subMsg)));
	    return existanceRole;
	    }

	@Override
	public RoleResponseDto create(RoleRequestDto request) {
		
		 if(roleRepository.existsByName(request.getName())){
			 String msg="Ce rôle existe déjà.";
			 audit.log(
					 AuditActionEnum.CREATE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
		        throw new RuntimeException(msg);
		    }
		 
		 Role role=Role.builder()
				 .name(request.getName())
				 .description(request.getDescription())
				 .build();
		 
		 Set<Permission> permission=new HashSet<Permission>();
		 
		 if(request.getPermissionIds()!=null){
			 
		 for (Long idPerm : request.getPermissionIds()) {
			 
			Permission p=permissionRepository.findById(idPerm)
					.orElseThrow(()->{
						String msg="Permission id %d introuvale.".formatted(idPerm);
						 audit.log(
								 AuditActionEnum.CREATE_ROLE, 
								 ModulesNameEnum.ROLE,
								 "❌ "+msg, 
								 false);
						 throw new RuntimeException(msg);
					}
					);
			permission.add(p);
		}
		 
		 role.setPermissions(permission);
		 }
		 Role saved=roleRepository.save(role);
		 audit.log(
				 AuditActionEnum.CREATE_ROLE, 
				 ModulesNameEnum.ROLE, 
				 "✅ Ajout de role %s ".formatted(role.getName()), 
				 true);
		return roleMapper.toResponse(saved);
	}

	@Override
	public RoleResponseDto update(Long id, RoleRequestDto request) {
		
		Role existanceRole=getById(id," pour une modification");
		
		boolean isnewroleExist=roleRepository.existsByName(request.getName());
		
		if(!isnewroleExist) {
			String msg="Le rôle %s n'existe pas.".formatted(request.getName());//déjà
			audit.log(
					 AuditActionEnum.UPDATE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			throw new RuntimeException(msg);
		}
		
		existanceRole.setDescription(request.getDescription());
		existanceRole.setName(request.getName());
		
		Set<Permission> permissions =
		        new HashSet<>();
		
		if(request.getPermissionIds()!=null) {
			
			 for(Long permissionId :
		            request.getPermissionIds()){

		        Permission permission =
		                permissionRepository
		                        .findById(permissionId)
		                        .orElseThrow(() ->{
		                        	String msg="Permission avec ID %d est introuvable.".formatted(permissionId);
		                        	audit.log(
		               					 AuditActionEnum.UPDATE_ROLE, 
		               					 ModulesNameEnum.ROLE,
		               					 "❌ "+msg, 
		               					 false);
		                        	return new EntityNotFoundException(msg);
		                        });

		        permissions.add(permission);

		    }
		}
		existanceRole.setPermissions(permissions);
		Role saved=roleRepository.save(existanceRole);
		 audit.log(
				 AuditActionEnum.UPDATE_ROLE, 
				 ModulesNameEnum.ROLE, 
				 "✅ Modification de role %s ".formatted(existanceRole.getName()), 
				 true);
		return roleMapper.toResponse(saved);
	}

	@Override
	public RoleResponseDto findById(Long id) {
		return roleMapper.toResponse(getById(id,""));
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleResponseDto> findAll() {
		return roleMapper.toResponses(roleRepository.findAll());
	}

	@Override
	public void delete(Long id) {
		Role existanceRole=getById(id," pour une suppression");
		
		if(userRepo.existsByRole_id(existanceRole.getId())) {
			String msg="Ce rôle est utilisé par un ou plusieurs utilisateurs.";
			audit.log(
  					 AuditActionEnum.DELETE_ROLE, 
  					 ModulesNameEnum.ROLE,
  					 "❌ "+msg, 
  					 false);
			throw new RuntimeException(msg);
		}
		roleRepository.deleteById(id);
		 audit.log(
				 AuditActionEnum.DELETE_ROLE, 
				 ModulesNameEnum.ROLE, 
				 "✅ Suppression de role %s ".formatted(existanceRole.getName()), 
				 true);
	}

	@Override
	public RoleResponseDto assignPermission(Long roleId, Long permissionId) {
		//recuperer le role a qui assigner les permissions
		Role role = getById(roleId, "");
		//recuperer la permission a assigner
	    Permission permission =
	            permissionRepository.findById(permissionId)
	                    .orElseThrow(() ->{
	                    	String msg="Permission introuvable.";
	                    	audit.log(
	               					 AuditActionEnum.ASSIGN_PERMISSION, 
	               					 ModulesNameEnum.ROLE,
	               					 "❌ "+msg, 
	               					 false);
	                    return	new EntityNotFoundException(msg);
	                    	
	                    });
	    //verififier si le role a deja la permission
	    boolean alreadyAssigned = role.getPermissions().stream()
	            .anyMatch(p -> p.getName().equals(permission.getName()));
	    
	    if (alreadyAssigned) {
	    	String msg="Cette permission est déjà attribuée.";
	    	audit.log(
  					 AuditActionEnum.ASSIGN_PERMISSION, 
  					 ModulesNameEnum.ROLE,
  					 "❌ "+msg, 
  					 false);
	        throw new RuntimeException(msg);

	    }

	    role.getPermissions().add(permission);
	    Role saved=roleRepository.save(role);
	    		 audit.log(
	    				 AuditActionEnum.ASSIGN_PERMISSION, 
	    				 ModulesNameEnum.ROLE, 
	    				 "✅ Assignation de permission %s  au role %s ".formatted(permission.getName(),role.getName()), 
	    				 true);
	    return roleMapper.toResponse(saved);

	}

	@Override
	public RoleResponseDto removePermission(Long roleId, Long permissionId) {
		//recuperer le role a qui assigner les permissions
				Role role = getById(roleId, "");
				//recuperer la permission a assigner
			    Permission permission =
			            permissionRepository.findById(permissionId)
			                    .orElseThrow(() ->{
			                    	String msg="Permission introuvable.";
			                    	audit.log(
			               					 AuditActionEnum.REMOVE_PERMISSION, 
			               					 ModulesNameEnum.ROLE,
			               					 "❌ "+msg, 
			               					 false);
			                    	return new EntityNotFoundException(msg);
			                    }
			                            );
			    
			    boolean alreadyAssigned = role.getPermissions().stream()
			            .anyMatch(p -> p.getName().equals(permission.getName()));
			    
			    if (alreadyAssigned) {
			    	String msg="Cette permission n'est pas attribuée à ce role.";
			    	audit.log(
          					 AuditActionEnum.REMOVE_PERMISSION, 
          					 ModulesNameEnum.ROLE,
          					 "❌ "+msg, 
          					 false);
			        throw new RuntimeException(msg);

			    }
			    role.getPermissions().remove(permission);
			    Role saved=roleRepository.save(role);
			    audit.log(
	    				 AuditActionEnum.REMOVE_PERMISSION, 
	    				 ModulesNameEnum.ROLE, 
	    				 "✅ Retrait de permission %s  au role %s ".formatted(permission.getName(),role.getName()), 
	    				 true);
		return roleMapper.toResponse(saved);
	}
	
}
