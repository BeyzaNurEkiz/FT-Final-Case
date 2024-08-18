package com.patika.ticketing.userservice.repository;

import com.patika.ticketing.userservice.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<T extends BaseUser> extends JpaRepository<T, Long> {
}
