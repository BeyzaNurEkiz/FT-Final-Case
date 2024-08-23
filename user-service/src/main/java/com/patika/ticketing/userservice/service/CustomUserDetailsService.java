package com.patika.ticketing.userservice.service;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.IndividualUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CorporateUserRepository corporateUserRepository;

    @Autowired
    private IndividualUserRepository individualUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kurumsal kullanıcıyı bul
        Optional<CorporateUser> corporateUserOpt = corporateUserRepository.findByUsername(username);
        if (corporateUserOpt.isPresent()) {
            CorporateUser corporateUser = corporateUserOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(corporateUser.getUsername())
                    .password(corporateUser.getPassword())
                    .authorities(corporateUser.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                            .collect(Collectors.toList()))
                    .build();
        }

        // Bireysel kullanıcıyı bul
        Optional<IndividualUser> individualUserOpt = individualUserRepository.findByUsername(username);
        if (individualUserOpt.isPresent()) {
            IndividualUser individualUser = individualUserOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(individualUser.getUsername())
                    .password(individualUser.getPassword())
                    .authorities(individualUser.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                            .collect(Collectors.toList()))
                    .build();
        }


        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
