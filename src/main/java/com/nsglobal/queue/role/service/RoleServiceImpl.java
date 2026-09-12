package com.nsglobal.queue.role.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
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
	    private Role getById(Long id) {
	    	Role existanceRole=roleRepository.findById(id)
					.orElseThrow(null);//()->new RuntimeException("Role introuvable %s".formatted(subMsg)));
	    return existanceRole;
	    }

	@Override
	public ApiResponse<RoleResponseDto> create(RoleRequestDto request) {
		
		 if(roleRepository.existsByName(request.getName())){
			
			 String msg=ApiMessages.ROLE_ALREADY_EXIST;
			 audit.log(
					 AuditActionEnum.CREATE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
		        return ResponseBuilder.error(msg);
		    }
		 
		 Role role=Role.builder()
				 .name(request.getName())
				 .description(request.getDescription())
				 .build();
		 
		 Set<Permission> permission=new HashSet<Permission>();
		 Set<String> errorsMsg =
			        new HashSet<String>();
		 
		 if(request.getPermissionIds()!=null&&!request.getPermissionIds().isEmpty()){
			 
		 for (Long idPerm : request.getPermissionIds()) {
			 
			Permission p=permissionRepository.findById(idPerm)
					.orElseThrow(null);
							if(p==null){
						String msg=ApiMessages.PERMISSION_NOT_FOUND.formatted(idPerm);
						 audit.log(
								 AuditActionEnum.CREATE_ROLE, 
								 ModulesNameEnum.ROLE,
								 "❌ "+msg, 
								 false);
						 errorsMsg.add(msg);
						// return ResponseBuilder.error(msg);
					}
					
			permission.add(p);
		}
		 
		 role.setPermissions(permission);
		 }
		 Role saved=roleRepository.save(role);
		 audit.log(
				 AuditActionEnum.CREATE_ROLE, 
				 ModulesNameEnum.ROLE, 
				 "✅ "+ApiMessages.PERMISSION_SUCCESS_ADD.formatted(role.getName()), 
				 true);
		return ResponseBuilder.success(ApiMessages.SUCCESS,roleMapper.toResponse(saved));
	}

	@Override
	public ApiResponse<RoleResponseDto> update(Long id, RoleRequestDto request) {
		
		Role existanceRole=getById(id);
		if(existanceRole==null) {
			String msg=ApiMessages.ROLE_NOT_FOUND;
			audit.log(
					 AuditActionEnum.CREATE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			return ResponseBuilder.error(msg);
		}
		
		boolean isnewroleExist=roleRepository.existsByName(request.getName());
		
		if(!isnewroleExist) {
			String msg="Le rôle %s n'existe pas.".formatted(request.getName());//déjà
			audit.log(
					 AuditActionEnum.UPDATE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			return ResponseBuilder.error(msg);
		}
		
		existanceRole.setDescription(request.getDescription());
		existanceRole.setName(request.getName());
		
		Set<Permission> permissions =
		        new HashSet<>();
		Set<String> errorsMsg =
		        new HashSet<String>();
		
		if(request.getPermissionIds()!=null&&!request.getPermissionIds().isEmpty()) {
			
			 for(Long permissionId :
		            request.getPermissionIds()){

		        Permission permission =
		                permissionRepository
		                        .findById(permissionId)
		                        .orElseThrow(null);
		                        		if(permission==null){
		                        	String msg=ApiMessages.PERMISSION_NOT_FOUND.formatted(permissionId);
		                        	audit.log(
		               					 AuditActionEnum.UPDATE_ROLE, 
		               					 ModulesNameEnum.ROLE,
		               					 "❌ "+msg, 
		               					 false);
		                        	//return new EntityNotFoundException(msg);
		                        	errorsMsg.add(msg);
		                        };

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
		return ResponseBuilder.success(ApiMessages.SUCCESS,roleMapper.toResponse(saved));
	}

	@Override
	public ApiResponse<RoleResponseDto> findById(Long id) {
		return ResponseBuilder.success(ApiMessages.SUCCESS, roleMapper.toResponse(getById(id)));
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<List<RoleResponseDto>> findAll() {
		return ResponseBuilder.success(ApiMessages.SUCCESS, roleMapper.toResponses(roleRepository.findAll()));
	}

	@Override
	public ApiResponse<RoleResponseDto> delete(Long id) {
		Role existanceRole=getById(id);
		if(existanceRole==null) {
			String msg=ApiMessages.ROLE_NOT_FOUND;
			audit.log(
					 AuditActionEnum.DELETE_ROLE, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			return ResponseBuilder.error(msg);
		}
		if(userRepo.existsByRole_id(existanceRole.getId())) {
			String msg="Ce rôle est utilisé par un ou plusieurs utilisateurs.";
			audit.log(
  					 AuditActionEnum.DELETE_ROLE, 
  					 ModulesNameEnum.ROLE,
  					 "❌ "+msg, 
  					 false);
		return	ResponseBuilder.error(msg);
		}
		existanceRole.setDeletedAt(LocalDateTime.now());
		Role saved=roleRepository.save(existanceRole);
		 audit.log(
				 AuditActionEnum.DELETE_ROLE, 
				 ModulesNameEnum.ROLE, 
				 "✅ Suppression de role %s ".formatted(existanceRole.getName()), 
				 true);
		 return ResponseBuilder.success(ApiMessages.SUCCESS,roleMapper.toResponse(saved));
	}

	@Override
	public ApiResponse<RoleResponseDto> assignPermission(Long roleId, Long permissionId) {
		//recuperer le role a qui assigner les permissions
		Role existanceRole=getById(roleId);
		if(existanceRole==null) {
			String msg=ApiMessages.ROLE_NOT_FOUND;
			audit.log(
					 AuditActionEnum.ASSIGN_PERMISSION, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			return ResponseBuilder.error(msg);
		}
		//recuperer la permission a assigner
	    Permission permission =
	            permissionRepository.findById(permissionId)
	                    .orElseThrow(null);
	                    if(permission==null){
	                    	String msg=ApiMessages.NOTFOUND;
	                    	audit.log(
	               					 AuditActionEnum.ASSIGN_PERMISSION, 
	               					 ModulesNameEnum.ROLE,
	               					 "❌ "+msg, 
	               					 false);
	                    return	ResponseBuilder.error(msg);
	                    	
	                    }
	    //verififier si le role a deja la permission
	    boolean alreadyAssigned = existanceRole.getPermissions().stream()
	            .anyMatch(p -> p.getName().equals(permission.getName()));
	    
	    if (alreadyAssigned) {
	    	String msg="Cette permission est déjà attribuée.";
	    	audit.log(
  					 AuditActionEnum.ASSIGN_PERMISSION, 
  					 ModulesNameEnum.ROLE,
  					 "❌ "+msg, 
  					 false);
	        return ResponseBuilder.error(msg);

	    }

	    existanceRole.getPermissions().add(permission);
	    Role saved=roleRepository.save(existanceRole);
	    		 audit.log(
	    				 AuditActionEnum.ASSIGN_PERMISSION, 
	    				 ModulesNameEnum.ROLE, 
	    				 "✅ Assignation de permission %s  au role %s ".formatted(permission.getName(),existanceRole.getName()), 
	    				 true);
	    return ResponseBuilder.success(ApiMessages.SUCCESS,roleMapper.toResponse(saved));

	}

	@Override
	public ApiResponse<RoleResponseDto> removePermission(Long roleId, Long permissionId) {
		//recuperer le role a qui assigner les permissions
		Role role=getById(roleId);
		if(role==null) {
			String msg=ApiMessages.ROLE_NOT_FOUND;
			audit.log(
					 AuditActionEnum.REMOVE_PERMISSION, 
					 ModulesNameEnum.ROLE,
					 "❌ "+msg, 
					 false);
			return ResponseBuilder.error(msg);
		}
				//recuperer la permission a assigner
			    Permission permission =
			            permissionRepository.findById(permissionId)
			                    .orElseThrow(null);
			                    if(permission==null){
			                    	String msg=ApiMessages.NOTFOUND;
			                    	audit.log(
			               					 AuditActionEnum.REMOVE_PERMISSION, 
			               					 ModulesNameEnum.ROLE,
			               					 "❌ "+msg, 
			               					 false);
			                    	return ResponseBuilder.error(msg);
			                    }
			    
			    boolean alreadyAssigned = role.getPermissions().stream()
			            .anyMatch(p -> p.getName().equals(permission.getName()));
			    
			    if (!alreadyAssigned) {
			    	String msg="Cette permission n'est pas attribuée à ce role.";
			    	audit.log(
          					 AuditActionEnum.REMOVE_PERMISSION, 
          					 ModulesNameEnum.ROLE,
          					 "❌ "+msg, 
          					 false);
			        return ResponseBuilder.error(msg);
			    }
			    
			    role.getPermissions().remove(permission);
			    Role saved=roleRepository.save(role);
			    
			    audit.log(
	    				 AuditActionEnum.REMOVE_PERMISSION, 
	    				 ModulesNameEnum.ROLE, 
	    				 "✅ Retrait de permission %s  au role %s ".formatted(permission.getName(),role.getName()), 
	    				 true);
			    
		return ResponseBuilder.success(ApiMessages.SUCCESS,roleMapper.toResponse(saved));//;
	}

	@Override
	public ApiResponse<List<Permission>> permissionFindAll() {
		// TODO Auto-generated method stub
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, permissionRepository.findAll());
	}
	
}
