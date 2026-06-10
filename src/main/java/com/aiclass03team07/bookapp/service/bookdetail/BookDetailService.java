package com.aiclass03team07.bookapp.service.bookdetail;

import com.aiclass03team07.bookapp.dto.bookdetail.DetailDTO;
import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    private final BookRepository bookRepository;

    //ㅁㄴ
    // 좋아요 +1
    public DetailDTO updateLikes(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
        long likeStatus = (book.getLikes() == null) ? 0L : book.getLikes();
        book.setLikes(likeStatus + 1);
        bookRepository.save(book);
        return toDTO(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Entity → DTO

    private DetailDTO toDTO(BookEntity entity) {
        DetailDTO dto = new DetailDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setLikes(entity.getLikes());
        dto.setContent(entity.getContent());
        dto.setGenre(entity.getGenre());
        dto.setPublisher(entity.getPublisher());
        dto.setSeriesInfo(entity.getSeriesInfo());
        dto.setPublishedDt(entity.getPublishedDt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 표지 이미지는 GenerateImageEntity 엔티티에 있고, 만들지 않았을 경우도 있음(null 체크)
        GenerateImageEntity image = entity.getGenerateImageEntity();
        if (image != null) {
            dto.setCoverImageUrl(image.getCoverImageUrl());
        }

        return dto;
    }
}