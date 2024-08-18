package com.patika.ticketing.userservice.entity.dto.response;

import com.patika.ticketing.userservice.entity.UserType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private Set<String> roles;

}
