package com.system.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.clinic.entity.PapelEntity;

import java.util.Optional;

public interface PapelRepository extends JpaRepository<PapelEntity, Long> {

    Optional<PapelEntity> findByPapel(String papel);

}
