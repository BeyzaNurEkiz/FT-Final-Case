package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateUserServiceImpl implements CorporateUserService {

    @Override
    public DataResult<CorporateResponse> register(SignUpCorporateRequest signUpCorporateRequest) {
        return null;
    }

    @Override
    public DataResult<CorporateResponse> update(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public DataResult<List<CorporateResponse>> findAll() {
        return null;
    }

    @Override
    public DataResult<CorporateResponse> findById(Long userId) {
        return null;
    }

    @Override
    public DataResult<CorporateResponse> findByUsername(String username) {
        return null;
    }

    @Override
    public Result deleteById(Long userId) {
        return null;
    }

}
