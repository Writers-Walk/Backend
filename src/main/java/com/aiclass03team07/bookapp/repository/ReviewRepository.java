package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    //Optional<ReviewEntity> findByBookEntityId(Long bookId);
    List<ReviewEntity> findAllByBookEntityId(Long bookId);
    Optional<ReviewEntity> findByBookEntity_Id(Long bookId);
}
