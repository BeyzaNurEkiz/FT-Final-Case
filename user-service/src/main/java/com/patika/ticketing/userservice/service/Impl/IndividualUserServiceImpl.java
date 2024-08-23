package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.service.IndividualUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IndividualUserServiceImpl implements IndividualUserService {

    @Override
    public DataResult<IndividualResponse> register(SignUpIndividualRequest signUpIndividualRequest) {
        return null;
    }

    @Override
    public DataResult<IndividualResponse> update(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public DataResult<List<IndividualResponse>> findAll() {
        return null;
    }

    @Override
    public DataResult<IndividualResponse> findById(Long userId) {
        return null;
    }

    @Override
    public DataResult<IndividualResponse> findByUsername(String username) {
        return null;
    }

    @Override
    public Result deleteById(Long userId) {
        return null;
    }

}
