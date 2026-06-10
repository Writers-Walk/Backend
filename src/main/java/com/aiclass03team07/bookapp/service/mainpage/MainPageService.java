package com.aiclass03team07.bookapp.service.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final BookRepository bookRepository;

    // 도서 목록 조회
    public Page<BookListDTO> getAllBooks(String keyword, Pageable pageable) {
        return bookRepository
                .findByTitleContainingOrAuthorContaining(keyword, keyword, pageable)
                .map(this::convertToBookListDTO);
    }

    // 좋아요 top N (전체 기준, 페이징과 무관)
    public List<BookListDTO> getTopRanking(String genre, int topN) {
        Pageable pageable = PageRequest.of(0, topN, Sort.by("likes").descending());

        Page<BookEntity> page = (genre == null || genre.isBlank())
                ? bookRepository.findAll(pageable)              // 전체 랭킹
                : bookRepository.findByGenre(genre, pageable);  // 장르별 랭킹

        return page.map(this::convertToBookListDTO).getContent();
    }
    public List<String> getGenres() {
        return bookRepository.findDistinctGenres();
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