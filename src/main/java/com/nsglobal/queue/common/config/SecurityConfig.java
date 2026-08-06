package com.nsglobal.queue.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.security.jwt.JwtAuthenticationEntryPoint;
import com.nsglobal.queue.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;

	private final JwtAuthenticationEntryPoint entryPoint;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
				
				/*
				 * Seules les requettes hhttp de l'api dans Authcontroller serons sans
				 * verification de l'authentification
				 * ApiRoutes.AUTH + 
				 * "/api/v1/auth"
				 **/
				.authorizeHttpRequests(auth ->
				auth
				/* Tout endpoint commencant par /notifications 
				 * sera accessible que par "ADMIN","SUPER_ADMIN"
				 * */
				//.requestMatchers(ApiRoutes.API_V1+"/notifications/**")
				//.hasAnyRole("ADMIN","SUPER_ADMIN")//
				/*
				 * tous endpoint comencant pat auth/ est public
				 * */
				.requestMatchers(ApiRoutes.AUTH +"/**",ApiRoutes.KIOSK +"/**","/ws/**")
				.permitAll().anyRequest().authenticated()
				
				);

		/**
		 * pour desactiver toutes les securites
		 */
		// .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
		// .httpBasic(Customizer.withDefaults());

		// ajout de filtre a http
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();

	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOrigins(List.of(
	            "http://localhost:4200"
	    ));

	    configuration.setAllowedMethods(List.of(
	            "GET",
	            "POST",
	            "PUT",
	            "PATCH",
	            "DELETE",
	            "OPTIONS"
	    ));

	    configuration.setAllowedHeaders(List.of("*"));

	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source =
	            new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}
}
