package com.nsglobal.queue.security.user;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;



/*
 * Spring Security ne connaît pas ton entité User.
Il faut donc créer un service qui lui explique comment charger un utilisateur.
 * */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	 private final UserRepository repository;
	 private final AuditService audit;
	
	 @Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("\n loadUserByUsername %s linge 1".formatted(username));
		
		//recuperation du user depuis la base par le nom d'utilisateur depuis le logingForm
		String userName=username;
		
		User usr=repository.findByUserName(userName)
				.orElseThrow(
						()->{
							audit.log(username, AuditActionEnum.LOGIN, ModulesNameEnum.SECURITY, "❌ Echec de connexion", false);
							throw new UsernameNotFoundException(username);
						}
						);
		
		System.out.println("\n loadUserByUsername %s linge 2".formatted(username));
		//retourne les info de user de la base charger dans user de spring security
		

List<SimpleGrantedAuthority> authorities_list=usr.getRole()
.getPermissions()
.stream()
.map(perm->new SimpleGrantedAuthority(
		perm.getName().name())
		).toList();

System.out.println(usr.getRole()
		.getPermissions()+" <<<< authorities_list ---> "+authorities_list);
		
		return org.springframework.security.core.userdetails
				.User
				.withUsername(username)
				.password(usr.getPassword())
				//chargement des niveau d'acces par permissions
				.authorities(authorities_list)
				.disabled(usr.isEnabled()==false)
				.accountLocked(usr.isLocked())
				//chargement des roles
				//.roles(usr.getRole().getName().name())
				.build();
				
	}

}
