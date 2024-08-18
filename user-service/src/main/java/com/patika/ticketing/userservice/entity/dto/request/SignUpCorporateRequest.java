package com.patika.ticketing.userservice.entity.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCorporateRequest extends SignUpBaseRequest {

    @NotBlank
    @Size(min = 10, max = 10)
    private String taxNumber;

}
