package com.aiclass03team07.bookapp.controller.generateimage;

import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/books/{bookId}")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class GenerateImageController {

    private final GenerateImageService generateImageService;

    /*
    * 1. 상세 페이지 켤 때 : 책 정보 + 기존 표지 한 번에 내려주기
    * 책을 한 번만 조회하고, 표지는 그 책에 연결된 엔티티에서 바로 꺼내기
    * */
    @GetMapping
    public Map<String, Object> getBookDetailWithImage(@PathVariable Long bookId) {
        BookEntity book = generateImageService.getBookById(bookId);

        GenerateImageResponseDto imageDto = GenerateImageResponseDto.from(book.getGenerateImageEntity());

        Map<String, Object> response = new HashMap<>();
        response.put("id", book.getId());
        response.put("title", book.getTitle());
        response.put("author", book.getAuthor());
        response.put("genre", book.getGenre());
        response.put("content", book.getContent());
        response.put("coverImageUrl", imageDto != null ? imageDto.getCoverImageUrl() : null);

        return response;
    }

    /*
    * 2. AI 표지 생성 버튼
    * */
    @PostMapping("/generate")
    public GenerateImageResponseDto generateImage(
            @PathVariable Long bookId,
            @Valid @RequestBody GenerateImageCreateRequestDto dto   //예외처리
    ){
        return generateImageService.generateAndSaveImage(bookId, dto);
    }

    /*
    * 3. 수동 저장/수정 버튼
    * */
    @PostMapping("/image")
    public GenerateImageResponseDto saveOrUpdateImage(
            @PathVariable Long bookId,
            @RequestBody GenerateImageRequestDto dto
    ){
        return generateImageService.saveOrUpdateImage(bookId, dto);
    }

    /*
    * 4. PATCH /api/books/{bookId}
    * 프론트엔드가 둘 중 어느 엔드포인트를 쓰는지 확인한 뒤, 쓰지 않는 쪽은 삭제하는 것을 권장
    * */
    @PatchMapping
    public ResponseEntity<GenerateImageResponseDto> updateBookCover(
            @PathVariable("bookId") Long bookId,
            @RequestBody GenerateImageRequestDto dto
    ){
        GenerateImageResponseDto responseDto = generateImageService.saveOrUpdateImage(bookId, dto);
        return ResponseEntity.ok(responseDto);
    }
}