package com.patika.ticketing.userservice.repository;

import com.patika.ticketing.userservice.entity.CorporateUser;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateUserRepository extends UserRepository<CorporateUser> {
}
