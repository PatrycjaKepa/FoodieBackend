//package com.foodie.application.Service.Configuration;
//
//import com.foodie.application.Service.Authentication.UserDetailsServiceImplementation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private UserDetailsServiceImplementation userDetailsServiceImplementation;
//
////    @Bean //implementacja usera testowego.
////    UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
////        UserDetails user = User.withUsername("test").password(passwordEncoder().encode("test"))
////                .authorities("read").build();
////        userDetailsService.createUser(user);
////        return userDetailsService;
////    }
//
//    @Bean
//    BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.httpBasic(Customizer.withDefaults());
//        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsServiceImplementation);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//}
