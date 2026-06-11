package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishListEntity, Long> {
    Optional<WishListEntity> findByUserIdAndBookId(Long userId, Long bookId);
    long countByBookId(Long bookId);
}