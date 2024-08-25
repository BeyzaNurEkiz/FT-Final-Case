package com.patika.ticketing.userservice.repository;

import com.patika.ticketing.userservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT token FROM Token token WHERE token.accessToken = :accessToken")
    Optional<Token> findByAccessToken(@Param("accessToken") String accessToken);

    @Query("SELECT token FROM Token token WHERE token.userId = :userId")
    Optional<Token> findByUserId(@Param("userId") Long userId);

    void deleteByUserId(@Param("userId") Long userId);

}
