package com.patika.ticketing.userservice.entity.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorporateUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    @Size(min = 10, max = 10)
    private String taxNumber;

}
