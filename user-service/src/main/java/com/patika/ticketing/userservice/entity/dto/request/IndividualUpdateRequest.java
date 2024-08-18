package com.patika.ticketing.userservice.entity.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    @Size(min = 11, max = 11)
    private String tcNumber;

}
