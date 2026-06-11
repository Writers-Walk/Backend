package com.aiclass03team07.bookapp.service.bookdetail;

import com.aiclass03team07.bookapp.dto.bookdetail.DetailDTO;
import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.entity.WishListEntity;
import com.aiclass03team07.bookapp.exception.BookNotFoundException;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.GenerateImageRepository;
import com.aiclass03team07.bookapp.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    private final BookRepository bookRepository;
    private final GenerateImageRepository generateImageRepository;
    private final WishlistRepository wishlistRepository;

    // id 정보조회.
    @Transactional(readOnly = true)
    public DetailDTO getBookDetail(Long id, Long userId) {

        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        GenerateImageEntity generateImage = entity.getGenerateImageEntity();

        DetailDTO dto = new DetailDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setContent(entity.getContent());
        dto.setGenre(entity.getGenre());
        dto.setPublisher(entity.getPublisher());
        dto.setSeriesInfo(entity.getSeriesInfo());
        dto.setPublicationDt(entity.getPublishedDt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 사용자 찜 - 비로그인시에는 x
        dto.setWished(userId != null
                && wishlistRepository.findByUserIdAndBookId(userId, id).isPresent());

        // 전체 찜 개수
        dto.setWishCount(wishlistRepository.countByBookId(id));

        // 표지 이미지
        dto.setCoverImageUrl(generateImage != null ? generateImage.getCoverImageUrl() : null);

        return dto;
    }

    // 찜하기 / 찜 취소 토글
    @Transactional
    public DetailDTO bookWishList(Long id, Long userId) {

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var existing = wishlistRepository.findByUserIdAndBookId(userId, id);

        boolean isWishedNow;
        long count = wishlistRepository.countByBookId(id);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
            isWishedNow = false;
            count = Math.max(0, count - 1);
        } else {
            WishListEntity wish = WishListEntity.builder()
                    .userId(userId)
                    .bookId(id)
                    .build();
            wishlistRepository.save(wish);
            isWishedNow = true;
            count = count + 1;
        }

        DetailDTO dto = toDTO(book);
        dto.setWished(isWishedNow);
        dto.setWishCount(count);

        return dto;
    }

    // 메인페이지 도서 목록 (찜 정보 포함)
    @Transactional(readOnly = true)
    public List<BookListDTO> getMainBookList(Long userId) {

        final Long finalUserId = userId;

        List<BookEntity> books = bookRepository.findAll();

        return books.stream().map(book -> {
            BookListDTO dto = new BookListDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setGenre(book.getGenre());
            dto.setCreatedAt(book.getCreatedAt());
            dto.setUpdatedAt(book.getUpdatedAt());

            if (book.getGenerateImageEntity() != null) {
                dto.setCoverImageUrl(book.getGenerateImageEntity().getCoverImageUrl());
            }

            dto.setWishCount(wishlistRepository.countByBookId(book.getId()));
            // 비로그인 x
            dto.setWished(finalUserId != null
                    && wishlistRepository.findByUserIdAndBookId(finalUserId, book.getId()).isPresent());

            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    private DetailDTO toDTO(BookEntity entity) {
        DetailDTO dto = new DetailDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setContent(entity.getContent());
        dto.setGenre(entity.getGenre());
        dto.setPublisher(entity.getPublisher());
        dto.setSeriesInfo(entity.getSeriesInfo());
        dto.setPublicationDt(entity.getPublishedDt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        GenerateImageEntity image = entity.getGenerateImageEntity();
        if (image != null) {
            dto.setCoverImageUrl(image.getCoverImageUrl());
        }

        return dto;
    }
}