package com.patika.ticketing.userservice.repository;

import com.patika.ticketing.userservice.entity.IndividualUser;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualUserRepository extends UserRepository<IndividualUser> {
}

