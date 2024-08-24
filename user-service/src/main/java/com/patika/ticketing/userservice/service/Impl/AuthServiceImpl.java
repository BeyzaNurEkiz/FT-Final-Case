package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.LoginRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.JwtResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.AuthService;
import com.patika.ticketing.userservice.service.mapper.CorporateUserMapper;
import com.patika.ticketing.userservice.utils.constants.ExceptionMessages;
import com.patika.ticketing.userservice.utils.constants.Messages;
import com.patika.ticketing.userservice.utils.exception.RoleNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UsernameAlreadyExistsException;
import com.patika.ticketing.userservice.utils.jwt.JwtUtils;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.SuccessDataResult;
import com.patika.ticketing.userservice.utils.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CorporateUserRepository corporateUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, CorporateUserRepository corporateUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.corporateUserRepository = corporateUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Authorities'leri String'e dönüştürme
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );

        return new SuccessDataResult<>(jwtResponse, "User authenticated successfully.");
    }

    @Override
    public DataResult<CorporateResponse> register(SignUpCorporateRequest signUpCorporateRequest) {
        if (corporateUserRepository.existsByUsername(signUpCorporateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (corporateUserRepository.existsByEmail(signUpCorporateRequest.getEmail())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }
        String encodedPassword = passwordEncoder.encode(signUpCorporateRequest.getPassword());
        CorporateUser userDto= (CorporateUser) CorporateUserMapper.INSTANCE.signUpRequestToUser(signUpCorporateRequest,encodedPassword);

        Set<String> strRoles = signUpCorporateRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        CorporateUser corporateUser=corporateUserRepository.save(userDto);

        return new SuccessDataResult<>(
                CorporateUserMapper.INSTANCE.userToUserResponse(corporateUser),
                Messages.USER_REGISTERED
        );
    }

    public Set<Role> mapToUserRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException(ExceptionMessages.ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException(ExceptionMessages.ROLE_NOT_FOUND));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException(ExceptionMessages.ROLE_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }
}
