/*
package com.patika.ticketing.trip_service.utils.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Tüm endpoint'leri güvenlikten muaf tutar.
                )
                .csrf(csrf -> csrf.disable());  // CSRF korumasını devre dışı bırakır.

        return http.build();
    }
}

 */
