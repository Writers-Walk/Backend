package com.aiclass03team07.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aiclass03team07.bookapp.entity.BookEntity;
public interface DetailRepository extends JpaRepository<BookEntity,Long> {

}
