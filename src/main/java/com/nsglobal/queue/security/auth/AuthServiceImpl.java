package com.nsglobal.queue.security.auth;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
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
	
	private final AuditService auditService;
	
	@Transactional
	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		
		User usr=userRepository.findByUserName(dto.getUserName())
				.orElseThrow(()->{
					String msg="Le nom d'utilisateur ou le mot depasse est invalide.";
					auditService.log(
		    				dto.getUserName(), 
		    				AuditActionEnum.LOGIN, 
		    				ModulesNameEnum.SECURITY, 
		    				"❌ Echec d'authentification de %s. Cause %s".formatted(dto.getUserName(),msg),
		    				false);
					return new InvalidCredentialsException(msg);
				}
						);
		
		String plainPwd=dto.getPassword();
		
		String HashedPwd=usr.getPassword();
		
		if(!passwordEncoder.matches(plainPwd, HashedPwd)) {
			String msg="Le mot de passe ou le nom d'utilisateur incorrecte.";
			auditService.log(
    				dto.getUserName(), 
    				AuditActionEnum.LOGIN, 
    				ModulesNameEnum.SECURITY, 
    				"❌ Echec d'authentification de %s. Cause %s".formatted(dto.getUserName(),msg),
    				false);
			throw new InvalidCredentialsException(msg);
		}
		
		 if (Boolean.FALSE.equals(usr.isEnabled())) {
			 String msg="Cet utilisateur n'est plus actif.";
			 auditService.log(
	    				dto.getUserName(), 
	    				AuditActionEnum.LOGIN, 
	    				ModulesNameEnum.SECURITY, 
	    				"❌ Echec d'authentification de %s. Cause %s".formatted(dto.getUserName(),msg),
	    				false);
	            throw new RuntimeException(msg);

	        }

	        if (Boolean.TRUE.equals(usr.isLocked())) {
	        	String msg="Cet utilisateur est bloqué";
	        	auditService.log(
	    				dto.getUserName(), 
	    				AuditActionEnum.LOGIN, 
	    				ModulesNameEnum.SECURITY, 
	    				"❌ Echec d'authentification de %s. Cause %s".formatted(dto.getUserName(),msg),
	    				false);
	            throw new RuntimeException(msg);

	        }

	        String token=jwtService.generateToken(usr);
	        usr.setLastLogin(LocalDate.now());
			userRepository.save(usr);
			
		LoginResponseDto resp=LoginResponseDto.builder()
				//.branchId(usr.getBranch().getId())
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
				.permissions(usr.getRole()
						.getPermissions()
						.stream()
						.map(p->p.getName()).toList())
				.build();
		
		auditService.log(
				usr.getUserName(), 
				AuditActionEnum.LOGIN, 
				ModulesNameEnum.SECURITY, 
				"✅ Login de %s réussit.".formatted(usr.getUserName()),
				true);
		return resp;
	}
	
}
