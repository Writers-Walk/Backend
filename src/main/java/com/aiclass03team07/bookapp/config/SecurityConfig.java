//package com.aiclass03team07.bookapp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers(
//                        "/swagger-ui.html",
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**"
//                ).permitAll()
//                .requestMatchers("/api/**").permitAll()
//                .anyRequest().authenticated());
//
//        return http.build();
//    }
//}
