package com.patika.ticketing.trip_service.entity.dto.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private String username;
    private String role;
    private String userType;
}
