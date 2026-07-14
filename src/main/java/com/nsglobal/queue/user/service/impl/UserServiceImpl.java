package com.nsglobal.queue.user.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.role.entity.Role;
import com.nsglobal.queue.role.repository.RoleRepository;
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
	
	private User getUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(
				()-> new RuntimeException("L'utilisateur recherché est introuvable.")
				);
		return user;
	}
	
	@Override
	public UserResponseDto create(UserRequestDto dto) {
		User usr=mapper.toEntity(dto);
		
		String encodedPassword=passwordEncoder.encode(dto.getPassword());
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(
				()->new RuntimeException("Le role choisi n'existe pas.")
				);
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(
				()->new RuntimeException("Le succursale de la banque choisi n'existe pas.")
				);
		
		usr.setBranch(branch);
		usr.setRole(role);
		usr.setPassword(encodedPassword);
		
		return mapper.toUserResponseDto(userRepository.save(usr));
	}

	@Override
	public UserResponseDto update(UserRequestDto dto, Long id) {
		
		User existance=getUserById(id);
		
		User newUser=mapper.toEntity(dto);
		
		Role role=roleRepo.findById(dto.getRole_id()).orElseThrow(
				()->new RuntimeException("Le role choisi n'existe pas.")
				);
		Branch branch=branchRepo.findById(dto.getRole_id()).orElseThrow(
				()->new RuntimeException("Le succursale de la banque choisi n'existe pas.")
				);
		
		existance.setLastName(newUser.getLastName());
		existance.setFirstName(newUser.getFirstName());
		existance.setUserName(newUser.getUserName());
		existance.setPhone(newUser.getPhone());
		
		existance.setBranch(branch);
		existance.setRole(role);
		
		return mapper.toUserResponseDto(userRepository.save(existance));
	}

	@Override
	public UserResponseDto findById(Long id) {

		return mapper.toUserResponseDto(getUserById(id));
	}

	@Override
	public List<UserResponseDto> findAll() {

		return mapper.toListUserResponseDto(userRepository.findAll());
	}

	@Override
	public void removeUser(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public UserResponseDto findByUserName(String userName) {
		User usr=userRepository.findByUserName(userName)
				.orElseThrow(()-> new RuntimeException("Le nom d'utilisateur est invalide"));
		
		return mapper.toUserResponseDto(usr);
	}


}
