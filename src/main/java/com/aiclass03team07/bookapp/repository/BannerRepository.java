package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
    Optional<BannerEntity> findTopByTypeOrderByCreatedAtDesc(String type);
}