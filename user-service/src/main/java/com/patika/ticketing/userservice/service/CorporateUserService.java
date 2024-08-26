package com.patika.ticketing.userservice.service;

import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;

import java.util.List;

public interface CorporateUserService {

    DataResult<CorporateResponse> update(UserUpdateRequest userUpdateRequest);
    DataResult<List<CorporateResponse>> findAll();
    DataResult<CorporateResponse> findById(Long userId);
    DataResult<CorporateResponse> findByUsername(String username);
    Result deleteById(Long userId);

}
