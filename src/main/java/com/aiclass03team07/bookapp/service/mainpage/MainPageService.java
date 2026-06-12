package com.aiclass03team07.bookapp.service.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final BookRepository bookRepository;
    private final WishlistRepository wishlistRepository;

    private static final Long HARDCODED_USER_ID = 1L;

    // 도서 목록 조회
    public Page<BookListDTO> getAllBooks(String keyword, Pageable pageable) {
        return bookRepository
                .findByTitleContainingOrAuthorContaining(keyword, keyword, pageable)
                .map(this::convertToBookListDTO);
    }

    // 찜 개수 top N
    public List<BookListDTO> getTopRanking(String genre, int topN) {
        return bookRepository.findAll().stream()
                .filter(b -> genre == null || genre.isBlank() || genre.equals(b.getGenre()))
                .map(this::convertToBookListDTO)
                .sorted(Comparator.comparingLong(
                        (BookListDTO dto) -> dto.getWishCount() == null ? 0L : dto.getWishCount()
                ).reversed())
                .limit(topN)
                .collect(Collectors.toList());
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
       // dto.setLikes(entity.getLikes());
        dto.setGenre(entity.getGenre());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 표지 이미지는 GenerateImageEntity 엔티티에 있고, 만들지 않았을 경우도 있음(null 체크)
        GenerateImageEntity image = entity.getGenerateImageEntity();
        if (image != null) {
            dto.setCoverImageUrl(image.getCoverImageUrl());
        }

        dto.setWishCount(wishlistRepository.countByBookId(entity.getId()));
        dto.setWished(wishlistRepository.findByUserIdAndBookId(HARDCODED_USER_ID, entity.getId()).isPresent());

        return dto;
    }
}