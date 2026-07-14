package com.nsglobal.queue.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("\n loadUserByUsername %s linge 1".formatted(username));
		
		//recuperation du user depuis la base par le nom d'utilisateur depuis le logingForm
		String userName=username;
		User usr=repository.findByUserName(userName)
				.orElseThrow(
						()->new UsernameNotFoundException(username)
						);
		System.out.println("\n loadUserByUsername %s linge 2".formatted(username));
		//retourne les info de user de la base charger dans user de spring security
		return org.springframework.security.core.userdetails
				.User
				.withUsername(username)
				.password(usr.getPassword())
				.authorities(usr.getRole().getName().name())
				.disabled(usr.isEnabled())
				.accountLocked(usr.isLocked())
				//.roles(usr.getRole().getName())
				.build();
				
	}

}
