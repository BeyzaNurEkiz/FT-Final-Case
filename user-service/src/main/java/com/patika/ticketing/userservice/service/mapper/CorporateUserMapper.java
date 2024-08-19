package com.patika.ticketing.userservice.service.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.Role;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;


import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CorporateUserMapper {
    CorporateUserMapper INSTANCE = Mappers.getMapper(CorporateUserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "encodedPassword", target = "password")
    })
    CorporateUser signUpRequestToUser(SignUpCorporateRequest signUpRequest, String encodedPassword);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "encodedPassword", target = "password")
    })
    CorporateUser userUpdateRequestToUser(UserUpdateRequest userUpdateRequest, String encodedPassword);

    @Mapping(target = "roles", ignore = true)
    CorporateResponse userToUserResponse(CorporateUser user);

    @IterableMapping(elementTargetType = CorporateResponse.class)
    List<CorporateResponse> usersToUserResponses(List<CorporateUser> photos);

    @AfterMapping
    default void setRoles(CorporateUser user, @MappingTarget CorporateResponse userResponse) {
        List<String> rolesAsStrings = user.getRoles().stream()
                .map(Role::getName)
                .map(Objects::toString)
                .toList();

        userResponse.setRoles((Set<String>) rolesAsStrings);
    }
}
