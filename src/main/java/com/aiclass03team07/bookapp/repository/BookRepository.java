package com.aiclass03team07.bookapp.repository;

import com.aiclass03team07.bookapp.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
