package com.nsglobal.queue.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.role.entity.Role;
import com.nsglobal.queue.role.repository.RoleRepository;
import com.nsglobal.queue.user.dto.UserPatchResponseDto;
import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.mapper.UserMapper;
import com.nsglobal.queue.user.repository.UserRepository;
import com.nsglobal.queue.user.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserMapper mapper;
	private final RoleRepository roleRepo;
	private final BranchRepository branchRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuditService audit;
	
	private User getUserById(Long id,AuditActionEnum action) {
		User user=userRepository.findById(id).orElseThrow(
				()->{
					String msg="L'utilisateur recherché est introuvable.";
					audit.log(action,ModulesNameEnum.USER , "❌ "+msg, false);
					throw new RuntimeException(msg);
				}
				);
		return user;
	}
	
	@Override
	public UserResponseDto create(UserRequestDto dto) {
		boolean existUser=userRepository.existsByUserName(dto.getUserName());
		//boolean existByPhone=userRepository.exists(dto.getPhone());
		if(existUser) {
			String msg="L'utilisateur %s existe déja.".formatted(dto.getUserName());
			audit.log(
					AuditActionEnum.CREATE_USER, 
					ModulesNameEnum.USER, 
					"❌ "+msg, 
					true);
			throw new RuntimeException(msg);
		}
		User usr=mapper.toEntity(dto);
		
		String encodedPassword=passwordEncoder.encode(dto.getPassword());
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(
				()->{
					String msg="Le role choisi n'existe pas.";
					audit.log(
							AuditActionEnum.CREATE_USER, 
							ModulesNameEnum.USER, 
							"❌ "+msg, 
							true);
					throw new RuntimeException(msg);
				}
				);
		
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(
				()->{
					String msg="L'agence de la banque choisi n'existe pas.";
					audit.log(
							AuditActionEnum.CREATE_USER, 
							ModulesNameEnum.USER, 
							"❌ "+msg, 
							true);
					throw	new RuntimeException(msg);
				}
				);
		
		usr.setBranch(branch);
		usr.setRole(role);
		usr.setPassword(encodedPassword);
		User saved=userRepository.save(usr);
		audit.log(
				AuditActionEnum.CREATE_USER, 
				ModulesNameEnum.USER, 
				"✅ création d'un nouveau utilisateur %s.".formatted(usr.getUserName()), 
				true);
		return mapper.toUserResponseDto(saved);
	}

	@Override
	public UserResponseDto update(UserRequestDto dto, Long id) {
		
		User existance=getUserById(id,AuditActionEnum.UPDATE_USER);
		
		User newUser=mapper.toEntity(dto);
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(
				()->{
					String msg="Le role choisi n'existe pas.";
					audit.log(AuditActionEnum.UPDATE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
				throw	new RuntimeException(msg);
				}
				);
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(
				()->{
					String msg="L'agence de la banque choisi n'existe pas.";
					audit.log(AuditActionEnum.UPDATE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
				throw	new RuntimeException(msg);
				}
				);
		
		existance.setLastName(newUser.getLastName());
		existance.setFirstName(newUser.getFirstName());
		existance.setUserName(newUser.getUserName());
		existance.setPhone(newUser.getPhone());
		
		existance.setBranch(branch);
		existance.setRole(role);
		User saved=userRepository.save(existance);
		audit.log(
				AuditActionEnum.UPDATE_ROLE, 
				ModulesNameEnum.USER, 
				"✅ Modification de l'utilisateur %s.".formatted(saved.getUserName()), 
				true);
		return mapper.toUserResponseDto(saved);
	}

	@Override
	public UserResponseDto findById(Long id) {
		return mapper.toUserResponseDto(getUserById(id,AuditActionEnum.VIEW_DETAIL));
	}

	
	@Override
	public UserPatchResponseDto removeUser(Long id) {
		User exists=getUserById(id,AuditActionEnum.DELETE_USER);
		exists.setDeletedAt(LocalDateTime.now());
		userRepository.save(exists);
		String msg="✅ L'utilisateur %s est supprimé.".formatted(exists.getUserName());
		audit.log(AuditActionEnum.DELETE_USER,ModulesNameEnum.USER ,
				msg,
				true);
		return UserPatchResponseDto
				.builder()
				.error(null)
				.success(true)
				.message(msg)
				.build();
	}

	@Override
	public UserResponseDto findByUserName(String userName) {
		User usr=userRepository.findByUserName(userName)
				.orElseThrow(()->{
			throw new RuntimeException("Le nom d'utilisateur est invalide");
		});
				
		return mapper.toUserResponseDto(usr);
	}

	@Override
	public UserPatchResponseDto enableDesableUser(Long userId, boolean isEnabled) {
		User exists=getUserById(userId,isEnabled?AuditActionEnum.ENABLE_USER:AuditActionEnum.DISABLE_USER);
		exists.setEnabled(isEnabled);
		userRepository.save(exists);
		String msg=isEnabled?"activé":"désactié";
		String msg2="✅ L'utilisateur %s est %s.".formatted(exists.getUserName(),msg);
		
		audit.log(isEnabled?AuditActionEnum.ENABLE_USER:AuditActionEnum.DISABLE_USER,ModulesNameEnum.USER ,
				msg2,
				true);
		
	return	UserPatchResponseDto
		.builder()
		.error(null)
		.success(true)
		.message(msg2)
		.build();
	}

	@Override
	public UserPatchResponseDto lockUnlockUserUser(Long userId, boolean isLocked) {
		User exists=getUserById(userId,isLocked?AuditActionEnum.LOCK_USER:AuditActionEnum.UNLOCK_USER);
		exists.setLocked(isLocked);
		userRepository.save(exists);
		String msg=isLocked?"bloqué":"débloqué";
		String msg2="✅ L'utilisateur %s est %s.".formatted(exists.getUserName(),msg);
		audit.log(isLocked?AuditActionEnum.LOCK_USER:AuditActionEnum.UNLOCK_USER,ModulesNameEnum.USER ,
				msg2,
				true);
		return UserPatchResponseDto
		.builder()
		.error(null)
		.success(true)
		.message(msg2)
		.build();
	}

	@Override
	public UserPatchResponseDto assignRole(Long userId, Long roleId) {
		User exists=getUserById(userId,AuditActionEnum.ASSIGN_ROLE);
		Role newRole=roleRepo.findById(roleId).orElseThrow(
				()->{
					String msg="Le rôle avec ID %d n'est pas retrouvé.".formatted(roleId);
					audit.log(
							AuditActionEnum.ASSIGN_ROLE, 
							ModulesNameEnum.ROLE, 
							"❌ "+msg, 
							false);
				throw new RuntimeException(msg);
				});
		
		exists.setRole(newRole);
		
		userRepository.save(exists);
		String msg="✅ Le rôle %s est assigné au user %s.".formatted(newRole.getName(),exists.getUserName());
		audit.log(AuditActionEnum.ASSIGN_ROLE,ModulesNameEnum.USER ,
				msg,
				true);
		return UserPatchResponseDto
				.builder()
				.error(null)
				.success(true)
				.message(msg)
				.build();
	}

	@Override
	public UserPatchResponseDto changeUserBranch(Long userId, Long branchId) {
		User exists=getUserById(userId,AuditActionEnum.CHANGE_BRANCH);
		Branch b=branchRepo.findById(branchId).
				orElseThrow(
						()->{
							String msg="L'agence avec identifiant %s ".formatted(branchId);
							audit.log(
									AuditActionEnum.CHANGE_BRANCH, 
									ModulesNameEnum.USER, 
									"❌ "+msg, 
									false);
							throw new RuntimeException(msg);
						});
		exists.setBranch(b);
		userRepository.save(exists);
		String msg="✅ Le rôle %s est la nouvelle agence de l'utilisateur %s.".formatted(b.getName(),exists.getUserName());
		audit.log(AuditActionEnum.ASSIGN_ROLE,ModulesNameEnum.USER ,
				msg,
				true);
		return UserPatchResponseDto
				.builder()
				.error(null)
				.success(true)
				.message(msg)
				.build();
	}

	@Override
	public List<UserResponseDto> findAll() {
		List <User> us=userRepository.findAll();
		return mapper.toListUserResponseDto(us);
	}


}
