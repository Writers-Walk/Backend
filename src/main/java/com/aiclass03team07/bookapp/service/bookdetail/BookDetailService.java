package com.aiclass03team07.bookapp.service.bookdetail;

import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    private final BookRepository bookRepository;

    // 좋아요 +1
    public BookCreateDTO updateLikes(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
        book.setLikes(book.getLikes() + 1);
        bookRepository.save(book);
        return toDTO(book);
    }

    // Entity → DTO
    private BookCreateDTO toDTO(BookEntity book) {
        return BookCreateDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishedDt(book.getPublishedDt())
                .seriesInfo(book.getSeriesInfo())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .content(book.getContent())
                .build();
    }
}