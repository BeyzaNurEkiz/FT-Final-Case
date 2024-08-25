package com.patika.ticketing.userservice.service;

import com.patika.ticketing.userservice.entity.dto.request.LoginRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.JwtResponse;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;

public interface AuthService {

    DataResult<JwtResponse> authenticateUser(LoginRequest loginRequest);
    DataResult<CorporateResponse> register(SignUpCorporateRequest signUpCorporateRequest);
    Result logout();
}
