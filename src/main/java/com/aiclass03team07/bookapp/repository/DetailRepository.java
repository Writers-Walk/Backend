package com.aiclass03team07.bookapp.repository;


import com.aiclass03team07.bookapp.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailRepository extends JpaRepository<BookDetail,Long> {
    List<BookDetail> findByTitle(String title);
    List<BookDetail> findByAuthor(String author);
    List<BookDetail> findByTitleContaining(String keyword);
    List<BookDetail> findByTitleAndAuthor(String title, String author);


}
