package com.nsglobal.queue.security.auth;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.security.auth.dto.LoginRequestDto;
import com.nsglobal.queue.security.auth.dto.LoginResponseDto;
import com.nsglobal.queue.security.auth.exception.InvalidCredentialsException;
import com.nsglobal.queue.security.jwt.JwtService;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final JwtService jwtService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		
		User usr=userRepository.findByUserName(dto.getUserName())
				.orElseThrow(()-> new InvalidCredentialsException("Le nom d'utilisateur ou le mot depasse est invalide."));
		
		String plainPwd=dto.getPassword();
		
		String HashedPwd=usr.getPassword();
		
		if(!passwordEncoder.matches(plainPwd, HashedPwd)) {
			
			throw new InvalidCredentialsException("Le mot de passe ou le nom d'utilisateur incorrecte.");
		}
		
		 if (Boolean.FALSE.equals(usr.isEnabled())) {

	            throw new RuntimeException("Cet utilisateur n'est plus actif.");

	        }

	        if (Boolean.TRUE.equals(usr.isLocked())) {

	            throw new RuntimeException("Cet utilisateur est fermé");

	        }

	        String token=jwtService.generateToken(usr);
		
		LoginResponseDto resp=LoginResponseDto.builder()
				.branchId(usr.getBranch().getId())
				.email(usr.getEmail())
				.token(token)
				.tokenType("Bearer")
				.role(usr.getRole().getName().name())
				.firstname(usr.getFirstName())
				.lastname(usr.getLastName())
				.username(usr.getUserName())
				.branchId(usr.getBranch()!= null?
						usr.getBranch().getId():
							null)
				.build();
				
		usr.setLastLogin(LocalDate.now());
		userRepository.save(usr);
		
		return resp;
	}
	
}
