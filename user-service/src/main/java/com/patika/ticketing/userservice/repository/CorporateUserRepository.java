package com.patika.ticketing.userservice.repository;

import com.patika.ticketing.userservice.entity.CorporateUser;
import com.patika.ticketing.userservice.entity.IndividualUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateUserRepository extends JpaRepository<CorporateUser, Long> {

    Optional<CorporateUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
