package com.patika.ticketing.userservice.service;

import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IndividualUserService {

    DataResult<IndividualResponse> register(SignUpIndividualRequest signUpIndividualRequest);

    DataResult<IndividualResponse> update(UserUpdateRequest userUpdateRequest);

    DataResult<List<IndividualResponse>> findAll();

    DataResult<IndividualResponse> findById(Long userId);

    DataResult<IndividualResponse> findByUsername(String username);

    Result deleteById(Long userId);

}
