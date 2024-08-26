package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.ERole;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.repository.IndividualUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.IndividualUserService;
import com.patika.ticketing.userservice.service.mapper.IndividualUserMapper;
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

    @Override
    public DataResult<IndividualResponse> update(UserUpdateRequest userUpdateRequest) {
        if (!individualUserRepository.existsById(userUpdateRequest.getId())) {
            throw new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }

        if (individualUserRepository.existsByUsername(userUpdateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_TAKEN);
        }

        if (individualUserRepository.existsByEmail(userUpdateRequest.getEmail())) {
            throw new EmailAlreadyExistsException(ExceptionMessages.EMAIL_ALREADY_TAKEN);
        }

        String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
        IndividualUser userDto = IndividualUserMapper.INSTANCE.userUpdateRequestToUser(userUpdateRequest, encodedPassword);

        Set<String> strRoles = userUpdateRequest.getRoles();
        Set<Role> roles = mapToUserRoles(strRoles);

        userDto.setRoles(roles);
        IndividualUser user = individualUserRepository.save(userDto);

        return new SuccessDataResult<>(
                IndividualUserMapper.INSTANCE.userToUserResponse(user),
                Messages.USER_UPDATED
        );
    }

    @Override
    public DataResult<List<IndividualResponse>> findAll() {
        List<IndividualUser> users = individualUserRepository.findAll();

        return new SuccessDataResult<>(
                IndividualUserMapper.INSTANCE.usersToUserResponses(users),
                Messages.USERS_LISTED
        );
    }

    @Override
    public DataResult<IndividualResponse> findById(Long userId) {
        IndividualResponse userResponse = individualUserRepository
                .findById(userId)
                .map(IndividualUserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        return new SuccessDataResult<>(userResponse, Messages.USER_FOUND);
    }

    @Override
    public DataResult<IndividualResponse> findByUsername(String username) {
        IndividualResponse userResponse = individualUserRepository
                .findByUsername(username)
                .map(IndividualUserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        return new SuccessDataResult<>(userResponse, Messages.USER_FOUND);
    }

    @Override
    public Result deleteById(Long userId) {
        IndividualUser user = individualUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));

        individualUserRepository.deleteById(user.getId());

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
