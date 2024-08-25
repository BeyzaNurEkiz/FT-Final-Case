package com.patika.ticketing.userservice.service.Impl;

import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.repository.CorporateUserRepository;
import com.patika.ticketing.userservice.repository.RoleRepository;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateUserServiceImpl implements CorporateUserService {

    private final CorporateUserRepository corporateUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CorporateUserServiceImpl(CorporateUserRepository corporateUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.corporateUserRepository = corporateUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
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
