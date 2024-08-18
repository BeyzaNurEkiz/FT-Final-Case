package com.patika.ticketing.userservice.entity.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpIndividualRequest extends SignUpBaseRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 10, max = 20)
    private String phoneNumber;

    @NotNull
    private UserType userType;

    @NotEmpty
    @Size(min = 1, max = 3)
    private Set<String> roles;

    @NotBlank
    @Size(min = 11, max = 11)
    private String tcNumber;

}
