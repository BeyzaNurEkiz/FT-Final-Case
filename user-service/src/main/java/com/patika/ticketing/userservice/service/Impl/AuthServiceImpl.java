package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.LoginRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.entity.dto.response.JwtResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.IndividualUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.repository.TokenRepository;
import com.patika.ticketing.userservice.service.AuthService;
import com.patika.ticketing.userservice.service.mapper.CorporateUserMapper;
import com.patika.ticketing.userservice.service.mapper.IndividualUserMapper;
import com.patika.ticketing.userservice.utils.constants.ExceptionMessages;
import com.patika.ticketing.userservice.utils.constants.Messages;
import com.patika.ticketing.userservice.utils.exception.RoleNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UsernameAlreadyExistsException;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import com.patika.ticketing.userservice.utils.result.SuccessDataResult;
import com.patika.ticketing.userservice.utils.result.SuccessResult;
import com.patika.ticketing.userservice.utils.security.JwtUtils;
import com.patika.ticketing.userservice.utils.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CorporateUserRepository corporateUserRepository;
    private final IndividualUserRepository individualUserRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, CorporateUserRepository corporateUserRepository, IndividualUserRepository individualUserRepository, TokenRepository tokenRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.corporateUserRepository = corporateUserRepository;
        this.individualUserRepository = individualUserRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT Token oluşturma
        String jwt = jwtUtils.generateTokenFromUserDetails((UserDetailsImpl) authentication.getPrincipal());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Kullanıcı rollerini String formatına dönüştürme
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
    public DataResult<IndividualResponse> registerIndividula(SignUpIndividualRequest signUpIndividualRequest) {
        if (individualUserRepository.existsByUsername(signUpIndividualRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (individualUserRepository.existsByEmail(signUpIndividualRequest.getEmail())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }

        String encodedPassword = passwordEncoder.encode(signUpIndividualRequest.getPassword());
        IndividualUser userDto = IndividualUserMapper.INSTANCE.signUpRequestToUser(signUpIndividualRequest, encodedPassword);

        Set<String> strRoles = signUpIndividualRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        IndividualUser individualUser = individualUserRepository.save(userDto);

        return new SuccessDataResult<>(
                IndividualUserMapper.INSTANCE.userToUserResponse(individualUser),
                Messages.USER_REGISTERED
        );
    }


    @Override
    public DataResult<CorporateResponse> registerCorporate(SignUpCorporateRequest signUpCorporateRequest) {
        if (corporateUserRepository.existsByUsername(signUpCorporateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (corporateUserRepository.existsByEmail(signUpCorporateRequest.getEmail())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }

        String encodedPassword = passwordEncoder.encode(signUpCorporateRequest.getPassword());
        CorporateUser userDto = CorporateUserMapper.INSTANCE.signUpRequestToUser(signUpCorporateRequest, encodedPassword);

        Set<String> strRoles = signUpCorporateRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        CorporateUser corporateUser = corporateUserRepository.save(userDto);

        return new SuccessDataResult<>(
                CorporateUserMapper.INSTANCE.userToUserResponse(corporateUser),
                Messages.USER_REGISTERED
        );
    }

    @Transactional
    @Override
    public Result logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!Objects.equals(principle.toString(), "anonymousUser")) {
            Long userId = ((UserDetailsImpl) principle).getId();
            tokenRepository.deleteByUserId(userId);
        }

        SecurityContextHolder.getContext().setAuthentication(null);

        SuccessResult result = new SuccessResult(Messages.USER_LOGGED_OUT);

        return result;
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
