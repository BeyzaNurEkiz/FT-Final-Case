package com.patika.ticketing.userservice.service.mapper;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.IndividualUser;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface IndividualUserMapper {

    IndividualUserMapper INSTANCE = Mappers.getMapper(IndividualUserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "encodedPassword", target = "password")
    })
    IndividualUser signUpRequestToUser(SignUpIndividualRequest signUpRequest, String encodedPassword);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "encodedPassword", target = "password")
    })
    IndividualUser userUpdateRequestToUser(UserUpdateRequest userUpdateRequest, String encodedPassword);

    @Mapping(target = "roles", ignore = true)
    IndividualResponse userToUserResponse(IndividualUser user);

    @IterableMapping(elementTargetType = IndividualResponse.class)
    List<IndividualResponse> usersToUserResponses(List<IndividualUser> photos);

    @AfterMapping
    default void setRoles(IndividualUser user, @MappingTarget IndividualResponse userResponse) {
        List<String> rolesAsStrings = user.getRoles().stream()
                .map(Role::getName)
                .map(Objects::toString)
                .toList();

        userResponse.setRoles((Set<String>) rolesAsStrings);
    }
}
