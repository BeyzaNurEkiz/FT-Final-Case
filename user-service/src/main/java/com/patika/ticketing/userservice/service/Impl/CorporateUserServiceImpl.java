package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.service.mapper.CorporateUserMapper;
import com.patika.ticketing.userservice.utils.constants.ExceptionMessages;
import com.patika.ticketing.userservice.utils.constants.Messages;
import com.patika.ticketing.userservice.utils.exception.RoleNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UsernameAlreadyExistsException;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import com.patika.ticketing.userservice.utils.result.SuccessDataResult;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CorporateUserServiceImpl implements CorporateUserService {

    private final CorporateUserRepository corporateUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CorporateUserServiceImpl(CorporateUserRepository corporateUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.corporateUserRepository = corporateUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
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

    @Transactional
    @Override
    public DataResult<CorporateResponse> update(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public DataResult<List<CorporateResponse>> findAll() {
        return null;
    }

    @Override
    public DataResult<CorporateResponse> findById(Long userId) {
        return null;
    }

    @Override
    public DataResult<CorporateResponse> findByUsername(String username) {
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
