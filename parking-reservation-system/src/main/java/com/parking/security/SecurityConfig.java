package com.parking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // your custom implementation
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // required for password matching
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/save", "/login", "/css/**", "/js/**","/images/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN") // ✅ Protect admin paths
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request,response,authentication)->{
                String role=authentication.getAuthorities().iterator().next().getAuthority();
                if(role.equals("ADMIN")) {
                	response.sendRedirect("/admin/dashboard");
                }else{
                	response.sendRedirect("/dashboard");
                }
                
                }) // ✅ Use custom redirection
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
            	.logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}