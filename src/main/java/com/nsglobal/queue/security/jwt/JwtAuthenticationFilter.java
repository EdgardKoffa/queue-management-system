package com.nsglobal.queue.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nsglobal.queue.security.user.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * C'est la classe qui est le cœur de Spring Security.
elle s'exécute AVANT tous les Controllers.
 * */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsServiceImpl userDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// recuperation de token par le header de la requette http par la cle
		// Authorization
		String header = request.getHeader("Authorization");

		// s'il n'y a pas de token
		if (header == null || !header.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);

			return;

		}

		// extraction de token en le separant de "Bearer ".
		int bearer_length = "Bearer ".length();

		String token = header.substring(bearer_length);// sous chaine de caractere apres "Bearer "

		// verification de la validite du token
		if (!jwtService.isTokenValid(token)) {

			filterChain.doFilter(request, response);

			return;
		}

		// extraction du nom d'utilisateur depuis le token
		String username = jwtService.extractUsername(token);

		// recuperation de l'utisateur charger dans spring.security par le user de la
		// base
		UserDetails user = userDetails.loadUserByUsername(username);
		
		//
		UsernamePasswordAuthenticationToken authentication =

				new UsernamePasswordAuthenticationToken(

						user,

						null,

						user.getAuthorities());
		//
		authentication.setDetails(

				new WebAuthenticationDetailsSource()

						.buildDetails(request));
		//
		SecurityContextHolder.getContext()

				.setAuthentication(authentication);
		
		//
		filterChain.doFilter(request, response);

	}

}
