package com.aiclass03team07.bookapp.service.bookdetail;

import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.dto.bookdetail.DetailDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.GenerateImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    private final BookRepository bookRepository;
    private final GenerateImageRepository generateImageRepository;

    //id 정보조회
    public DetailDTO getBookDetail(Long id){

        BookEntity entity = bookRepository.findById(id).orElse(null);
        GenerateImageEntity generateImage = entity.getGenerateImageEntity();
        Long generateImageId = (generateImage != null) ? generateImage.getId() : null;
        DetailDTO dto = new DetailDTO();
        dto.setLikes(entity.getLikes());
        dto.setContent(entity.getContent());
        dto.setGenre(entity.getGenre());
        dto.setAuthor(entity.getAuthor());
        dto.setPublisher(entity.getPublisher());
        dto.setTitle(entity.getTitle());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setSeriesInfo(entity.getSeriesInfo());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPublicationDt(entity.getPublishedDt());
        if(generateImageId != null){
            String img_url = generateImageRepository.findById(generateImageId)
                    .map(GenerateImageEntity::getCoverImageUrl)
                    .orElse(null);
            dto.setCoverImageUrl(img_url);
        }
        else
            dto.setCoverImageUrl(null);

        return dto;
    }
    //ㅁㄴ
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