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

    // 도서 목록 조회 (세션 유저 기준 찜 여부 포함)
    public Page<BookListDTO> getAllBooks(String keyword, Pageable pageable, Long userId) {
        return bookRepository
                .findByTitleContainingOrAuthorContaining(keyword, keyword, pageable)
                .map(book -> convertToBookListDTO(book, userId));
    }

    // 찜 개수 top N (전체 또는 장르별) — 랭킹은 세션 비인식
    public List<BookListDTO> getTopRanking(String genre, int topN) {
        return bookRepository.findAll().stream()
                .filter(b -> genre == null || genre.isBlank() || genre.equals(b.getGenre()))
                .map(b -> convertToBookListDTO(b, null))
                .sorted(Comparator.comparingLong(
                        (BookListDTO dto) -> dto.getWishCount() == null ? 0L : dto.getWishCount()
                ).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    public List<String> getGenres() {
        return bookRepository.findDistinctGenres();
    }

    // entity -> dto 변환 (userId null이면 wished=false)
    private BookListDTO convertToBookListDTO(BookEntity entity, Long userId) {
        BookListDTO dto = new BookListDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setGenre(entity.getGenre());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        GenerateImageEntity image = entity.getGenerateImageEntity();
        if (image != null) {
            dto.setCoverImageUrl(image.getCoverImageUrl());
        }

        dto.setWishCount(wishlistRepository.countByBookId(entity.getId()));
        dto.setWished(userId != null
                && wishlistRepository.findByUserIdAndBookId(userId, entity.getId()).isPresent());
        return dto;
    }
}