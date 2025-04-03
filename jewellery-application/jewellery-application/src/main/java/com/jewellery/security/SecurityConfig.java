package com.jewellery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
		@Autowired
		private UserDetailsService userDetailsService;
		@Autowired
		JwtFilter jwtFilter;
	 
		@Bean
		public AuthenticationProvider authProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(userDetailsService);
			provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
			return provider;
		}

	 
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.csrf(Customizer -> Customizer.disable())
				.authorizeHttpRequests(request -> request
					.requestMatchers("/auth/register").permitAll()
					.requestMatchers("/auth/signin").permitAll()
					//.requestMatchers("/category/*").hasRole("VENDOR")
					//.requestMatchers("/product/*").hasRole("VENDOR")
					.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

			return http.build();
		}

		@Bean
		public AuthenticationManager authenticatinManager(AuthenticationConfiguration config) throws Exception{
			return config.getAuthenticationManager();
		}
//		@Bean
//		public UserDetailsService userDetailsService() {
//			
//			UserDetails user =User.withDefaultPasswordEncoder()
//					.username("Priyusha")
//					.password("1234")
//					.roles("USER").build();
//			
//			return new InMemoryUserDetailsManager(user);
//		}
	 
	}