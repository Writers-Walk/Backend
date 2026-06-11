package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
