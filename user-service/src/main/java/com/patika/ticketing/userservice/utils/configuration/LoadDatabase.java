package com.patika.ticketing.userservice.utils.configuration;

import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            // ROLE_USER kontrolü ve ekleme
            Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole.isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_USER));
            }

            // ROLE_ADMIN kontrolü ve ekleme
            Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            if (adminRole.isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
            }
        };
    }
}