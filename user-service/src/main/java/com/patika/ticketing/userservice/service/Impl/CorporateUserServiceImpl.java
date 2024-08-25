package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.service.mapper.CorporateUserMapper;
import com.patika.ticketing.userservice.utils.constants.ExceptionMessages;
import com.patika.ticketing.userservice.utils.constants.Messages;
import com.patika.ticketing.userservice.utils.exception.EmailAlreadyExistsException;
import com.patika.ticketing.userservice.utils.exception.RoleNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UserNotFoundException;
import com.patika.ticketing.userservice.utils.exception.UsernameAlreadyExistsException;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import com.patika.ticketing.userservice.utils.result.SuccessDataResult;
import com.patika.ticketing.userservice.utils.result.SuccessResult;
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
    public DataResult<CorporateResponse> update(UserUpdateRequest userUpdateRequest) {
        if (!corporateUserRepository.existsById(userUpdateRequest.getId())) {
            throw new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }

        if (corporateUserRepository.existsByUsername(userUpdateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (corporateUserRepository.existsByEmail(userUpdateRequest.getEmail())) {
            throw new EmailAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }

        String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
        CorporateUser userDto = CorporateUserMapper.INSTANCE.userUpdateRequestToUser(userUpdateRequest, encodedPassword);

        Set<String> strRoles = userUpdateRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        CorporateUser user = corporateUserRepository.save(userDto);

        return new SuccessDataResult<>(
                CorporateUserMapper.INSTANCE.userToUserResponse(user),
                Messages.USER_UPDATED
        );
    }


    @Override
    public DataResult<List<CorporateResponse>> findAll() {
        List<CorporateUser> users = corporateUserRepository.findAll();

        return new SuccessDataResult<>(
                CorporateUserMapper.INSTANCE.usersToUserResponses(users),
                Messages.USERS_LISTED
        );
    }

    @Override
    public DataResult<CorporateResponse> findById(Long userId) {
        CorporateResponse userResponse = corporateUserRepository
                .findById(userId)
                .map(CorporateUserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        return new SuccessDataResult<>(userResponse, Messages.USER_FOUND);
    }

    @Override
    public DataResult<CorporateResponse> findByUsername(String username) {
        CorporateResponse userResponse = corporateUserRepository
                .findByUsername(username)
                .map(CorporateUserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        return new SuccessDataResult<>(userResponse, Messages.USER_FOUND);
    }

    @Override
    public Result deleteById(Long userId) {
        CorporateUser user = corporateUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        corporateUserRepository.deleteById(user.getId());

        return new SuccessResult(Messages.USER_DELETED);
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
