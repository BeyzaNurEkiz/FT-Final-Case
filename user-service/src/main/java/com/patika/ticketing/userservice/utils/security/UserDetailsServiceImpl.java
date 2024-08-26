package com.patika.ticketing.userservice.utils.security;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.IndividualUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CorporateUserRepository corporateUserRepository;

    @Autowired
    IndividualUserRepository individualUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CorporateUser corporateUser = corporateUserRepository.findByUsername(username).orElse(null);
        if (corporateUser != null) {
            return UserDetailsImpl.build(corporateUser);
        }

        IndividualUser individualUser = individualUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(individualUser);
    }
}
