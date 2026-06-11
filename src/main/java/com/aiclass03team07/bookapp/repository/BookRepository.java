package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    // 제목 또는 저자에 keyword가 포함된 책 검색 (+ 정렬)
    Page<BookEntity> findByTitleContainingOrAuthorContaining(
            String title, String author, Pageable pageable);
    Page<BookEntity> findByGenre(String genre, Pageable pageable);

    @Query("SELECT DISTINCT b.genre FROM BookEntity b WHERE b.genre IS NOT NULL ORDER BY b.genre")
    List<String> findDistinctGenres();

    //좋아요가 가장 많은 책 1권 가져오기
    BookEntity findTopByOrderByLikesDesc();
}
