package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.IndividualUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.IndividualUserService;
import com.patika.ticketing.userservice.service.mapper.CorporateUserMapper;
import com.patika.ticketing.userservice.service.mapper.IndividualUserMapper;
import com.patika.ticketing.userservice.utils.constants.ExceptionMessages;
import com.patika.ticketing.userservice.utils.constants.Messages;
import com.patika.ticketing.userservice.utils.exception.RoleNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UsernameAlreadyExistsException;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import com.patika.ticketing.userservice.utils.result.SuccessDataResult;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IndividualUserServiceImpl implements IndividualUserService {

    private final IndividualUserRepository individualUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public IndividualUserServiceImpl(IndividualUserRepository individualUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.individualUserRepository = individualUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public DataResult<IndividualResponse> register(SignUpIndividualRequest signUpIndividualRequest) {
        if (individualUserRepository.existsByUsername(signUpIndividualRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (individualUserRepository.existsByEmail(signUpIndividualRequest.getEmail())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }
        String encodedPassword = passwordEncoder.encode(signUpIndividualRequest.getPassword());
        IndividualUser userDto= (IndividualUser) IndividualUserMapper.INSTANCE.signUpRequestToUser(signUpIndividualRequest,encodedPassword);

        Set<String> strRoles = signUpIndividualRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        IndividualUser individualUser=individualUserRepository.save(userDto);

        return new SuccessDataResult<>(
                IndividualUserMapper.INSTANCE.userToUserResponse(individualUser),
                Messages.USER_REGISTERED
        );
    }

    @Override
    public DataResult<IndividualResponse> update(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public DataResult<List<IndividualResponse>> findAll() {
        return null;
    }

    @Override
    public DataResult<IndividualResponse> findById(Long userId) {
        return null;
    }

    @Override
    public DataResult<IndividualResponse> findByUsername(String username) {
        return null;
    }

    @Override
    public Result deleteById(Long userId) {
        return null;
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
