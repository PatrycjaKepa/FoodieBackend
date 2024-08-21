package com.foodie.application.Service.Configuration;

import com.foodie.application.Entity.User;
import com.foodie.application.Service.Authentication.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicAuthSecurity {

    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public BasicAuthSecurity(RestAuthenticationEntryPoint authenticationEntryPoint, UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/api/v1/user/register").permitAll()
                    .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
//                .authenticationEntryPoint(authenticationEntryPoint)
                .build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(PasswordEncoderConfig.passwordEncoder())
//                .withUser("user1")
//                .password(PasswordEncoderConfig.passwordEncoder().encode("password"))
//                .roles("ADMIN");
        auth.userDetailsService(userDetailsServiceImplementation)
                .passwordEncoder(PasswordEncoderConfig.passwordEncoder());
    }
}
