package com.system.clinic.repository;

import com.system.clinic.entity.RedefinicaoSenhaToken;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RedefinicaoSenhaTokenRepository extends JpaRepository<RedefinicaoSenhaToken, Long> {
 Optional<RedefinicaoSenhaToken> findByToken(String token);    
 
    @Modifying
    @Transactional
    @Query("DELETE FROM RedefinicaoSenhaToken t WHERE t.email = :email")
    void deleteByEmail(@Param("email") String email);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM RedefinicaoSenhaToken t WHERE t.dataExpiracao < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
}