package com.aiclass03team07.bookapp.service.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final BookRepository bookRepository;

    // 도서 목록 조회
    public List<BookListDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToBookListDTO)
                .collect(Collectors.toList());
    }

    // entity -> dto 변환
    private BookListDTO convertToBookListDTO(BookEntity entity) {
        BookListDTO dto = new BookListDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setLikes(entity.getLikes());
        dto.setGenre(entity.getGenre());
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