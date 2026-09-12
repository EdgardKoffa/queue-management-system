package com.nsglobal.queue.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
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
	
	private User getUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(null);
			/*	()->{
					String msg="L'utilisateur recherché est introuvable.";
					audit.log(action,ModulesNameEnum.USER , "❌ "+msg, false);
					throw new RuntimeException(msg);
				}*/
				
		return user;
	}
	@Transactional
	@Override
	public ApiResponse<UserResponseDto> create(UserRequestDto dto) {
		boolean existUser=userRepository.existsByUserName(dto.getUserName());
		//boolean existByPhone=userRepository.exists(dto.getPhone());
		if(existUser) {
			String msg=ApiMessages.USER_EXISTE.formatted(dto.getUserName());
			audit.log(
					AuditActionEnum.CREATE_USER, 
					ModulesNameEnum.USER, 
					"❌ "+msg, 
					true);
			ApiPageResponse.builder()
			.build();
			return ResponseBuilder.error(msg);
		}
		User usr=mapper.toEntity(dto);
		
		String encodedPassword=passwordEncoder.encode(dto.getPassword());
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(null);
			if(role==null){
					String msg=ApiMessages.ROLE_NOT_FOUND;
					audit.log(
							AuditActionEnum.CREATE_USER, 
							ModulesNameEnum.USER, 
							"❌ "+msg, 
							false);
					return ResponseBuilder.error(msg);
				}
				
		
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(null);
				if(branch==null){
					String msg=ApiMessages.NOTFOUND_BRANCH;
					audit.log(
							AuditActionEnum.CREATE_USER, 
							ModulesNameEnum.USER, 
							"❌ "+msg, 
							false);
					return ResponseBuilder.error(msg);
				}
			
		
		usr.setBranch(branch);
		usr.setRole(role);
		usr.setPassword(encodedPassword);
		User saved=userRepository.save(usr);
		audit.log(
				AuditActionEnum.CREATE_USER, 
				ModulesNameEnum.USER, 
				"✅ "+ApiMessages.USER_CREATE_SUCCESS.formatted(usr.getUserName()), 
				true);
		return ResponseBuilder.success(ApiMessages.USER_CREATE_SUCCESS,mapper.toUserResponseDto(saved));
	}
	
	@Transactional
	@Override
	public ApiResponse<UserResponseDto> update(UserRequestDto dto, Long id) {
		
		User existance=getUserById(id);
		if(existance==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(AuditActionEnum.UPDATE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		
		User newUser=mapper.toEntity(dto);
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(null);
				if(role==null){
					String msg=ApiMessages.ROLE_NOT_FOUND;
					audit.log(AuditActionEnum.UPDATE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
				return ResponseBuilder.error(msg);
				}
				
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(null);
				if(branch==null){
					String msg=ApiMessages.NOTFOUND_BRANCH;
					audit.log(AuditActionEnum.UPDATE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
				return ResponseBuilder.error(msg);
				}
				
		
		existance.setLastName(newUser.getLastName());
		existance.setFirstName(newUser.getFirstName());
		existance.setUserName(newUser.getUserName());
		existance.setPhone(newUser.getPhone());
		
		existance.setBranch(branch);
		existance.setRole(role);
		User saved=userRepository.save(existance);
		audit.log(
				AuditActionEnum.UPDATE_USER, 
				ModulesNameEnum.USER, 
				"✅ "+ApiMessages.USER_UPDATED.formatted(saved.getUserName()), 
				true);
		return ResponseBuilder.success(ApiMessages.UPDATED, mapper.toUserResponseDto(saved));
	}
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<UserResponseDto> findById(Long id) {
		return ResponseBuilder.success(ApiMessages.SUCCESS, mapper.toUserResponseDto(getUserById(id)));
	}

	@Transactional
	@Override
	public ApiResponse<UserResponseDto> removeUser(Long id) {
		User exists=getUserById(id);
		if(exists==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(AuditActionEnum.DELETE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		exists.setDeletedAt(LocalDateTime.now());
		userRepository.save(exists);
		String msg=ApiMessages.USER_DELETED.formatted(exists.getUserName());
		String msge="✅ "+msg;
		audit.log(AuditActionEnum.DELETE_USER,ModulesNameEnum.USER ,
				msge,
				true);
		return ResponseBuilder.success(msg, mapper.toUserResponseDto(exists));
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<UserResponseDto> findByUserName(String userName) {
		User usr=userRepository.findByUserName(userName)
				.orElseThrow(null);
		if(usr==null){
			
			return ResponseBuilder.error(ApiMessages.USER_NAME_NOT_EXIST);
		}
				
		return ResponseBuilder.success(ApiMessages.SUCCESS,mapper.toUserResponseDto(usr));
	}

	@Transactional
	@Override
	public ApiResponse<UserResponseDto> enableDesableUser(Long userId, boolean isEnabled) {
		User exists=getUserById(userId);
		if(exists==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(isEnabled==true?AuditActionEnum.ENABLE_USER:AuditActionEnum.DISABLE_USER,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		exists.setEnabled(isEnabled);
		User saved=userRepository.save(exists);
		String msg=isEnabled?ApiMessages.ENABLE:ApiMessages.DISABLE;
		String msg2="✅ "+ApiMessages.USER_PARAM_MSG.formatted(exists.getUserName(),msg);
		
		audit.log(isEnabled?AuditActionEnum.ENABLE_USER:AuditActionEnum.DISABLE_USER,ModulesNameEnum.USER ,
				msg2,
				true);
		
	return	ResponseBuilder.success(msg2, mapper.toUserResponseDto(saved));
	}
	
	@Transactional
	@Override
	public ApiResponse<UserResponseDto> lockUnlockUserUser(Long userId, boolean isLocked) {
		User exists=getUserById(userId);
		if(exists==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(isLocked==true?AuditActionEnum.LOCK_USER:AuditActionEnum.UNLOCK_USER,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		exists.setLocked(isLocked);
		User saved=userRepository.save(exists);
		String msg=isLocked?ApiMessages.LOCK:ApiMessages.UNLOCK;
		String msg2="✅ "+ApiMessages.USER_PARAM_MSG.formatted(exists.getUserName(),msg);
		audit.log(isLocked?AuditActionEnum.LOCK_USER:AuditActionEnum.UNLOCK_USER,ModulesNameEnum.USER ,
				msg2,
				true);
		return ResponseBuilder.success(msg2, mapper.toUserResponseDto(saved));
	}
	
	@Transactional
	@Override
	public ApiResponse<UserResponseDto> assignRole(Long userId, Long roleId) {
		User exists=getUserById(userId);
		//
		if(exists==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(AuditActionEnum.ASSIGN_ROLE,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		Role newRole=roleRepo.findById(roleId).orElseThrow(null);
				if(newRole==null){
					String msg=ApiMessages.ROLE_NOT_FOUND;
					audit.log(
							AuditActionEnum.ASSIGN_ROLE, 
							ModulesNameEnum.ROLE, 
							"❌ "+msg, 
							false);
					return ResponseBuilder.error(msg);
				}
		
		exists.setRole(newRole);
		
		User saved=userRepository.save(exists);
		
		String msg=ApiMessages.ROLE_ASSIGNED.formatted(newRole.getName(),exists.getUserName());
		audit.log(AuditActionEnum.ASSIGN_ROLE,ModulesNameEnum.USER ,
				"✅ "+msg,
				true);
		return ResponseBuilder.success(msg, mapper.toUserResponseDto(saved));
	}
	
	@Transactional
	@Override
	public ApiResponse<UserResponseDto> changeUserBranch(Long userId, Long branchId) {
		User exists=getUserById(userId);
		if(exists==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			audit.log(AuditActionEnum.CHANGE_BRANCH,ModulesNameEnum.USER , "❌ "+msg, false);
		return ResponseBuilder.error(msg);
		}
		
		Branch b=branchRepo.findById(branchId).
				orElseThrow(null);
						if(b==null){
							String msg=ApiMessages.NOTFOUND_BRANCH;
							
							audit.log(
									AuditActionEnum.CHANGE_BRANCH, 
									ModulesNameEnum.USER, 
									"❌ "+msg, 
									false);
							ResponseBuilder.error(msg);
						}
						
		exists.setBranch(b);
	User saved=	userRepository.save(exists);
		String msg=ApiMessages.SUCCESS;
		audit.log(AuditActionEnum.CHANGE_BRANCH,
				ModulesNameEnum.USER ,
				"✅ "+msg,
				true);
		return ResponseBuilder.success(msg, mapper.toUserResponseDto(saved));
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<List<UserResponseDto>> findAll() {
		List <User> us=userRepository.findAll();
		return ResponseBuilder.success(ApiMessages.SUCCESS,mapper.toListUserResponseDto(us));
	}
	public ApiPageResponse<UserResponseDto> findAll(Pageable pageable) {
		Page <User> us=userRepository.findAll(pageable);
		return ResponseBuilder.page(ApiMessages.SUCCESS,us.map(mapper::toUserResponseDto));
	}


}
