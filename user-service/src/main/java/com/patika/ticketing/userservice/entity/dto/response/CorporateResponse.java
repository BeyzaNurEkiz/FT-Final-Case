package com.patika.ticketing.userservice.entity.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorporateResponse extends BaseResponse {

    private String taxNumber;

}
