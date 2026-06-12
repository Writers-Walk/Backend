package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishListEntity, Long> {
    Optional<WishListEntity> findByUserIdAndBookId(Long userId, Long bookId);
    long countByBookId(Long bookId);

    List<WishListEntity> findByUserId(Long userId);

    // 찜 가장 많은 책 1권의 bookId ✅
    @Query("SELECT w.bookId FROM WishListEntity w GROUP BY w.bookId ORDER BY COUNT(w.id) DESC LIMIT 1")
    Optional<Long> findMostWishedBookId();
}
