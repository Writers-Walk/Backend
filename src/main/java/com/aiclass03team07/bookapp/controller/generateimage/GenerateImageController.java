//package com.aiclass03team07.bookapp.controller.generateimage;
//
//import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
//import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
//import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
//import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
////@RequestMapping("/books/{bookId}/image")
//@RequestMapping("/api/books/{bookId}")
//@RequiredArgsConstructor
//public class GenerateImageController {
//
//    private final GenerateImageService generateImageService;
//
//    @PostMapping
//    public GenerateImageResponseDto saveOrUpdateImage(
//            @PathVariable Long bookId,
//            @RequestBody GenerateImageRequestDto dto
//    ){
//        return generateImageService.saveOrUpdateImage(bookId, dto);
//    }
//
//    @GetMapping
//    public GenerateImageResponseDto getImageByBookId(@PathVariable Long bookId){
//        return generateImageService.getImageByBookId(bookId);
//    }
//
//    @PostMapping("/generate")
//    public GenerateImageResponseDto generateImage(
//            @PathVariable Long bookId,
//            @RequestBody GenerateImageCreateRequestDto dto
//    ){
//        return generateImageService.generateAndSaveImage(bookId, dto);
//    }
//}
package com.aiclass03team07.bookapp.controller.generateimage;

import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/books/{bookId}")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GenerateImageController {


    private final GenerateImageService generateImageService;

    // 🌟 1. [본질적 해결] 프론트엔드가 페이지 켤 때 H2 DB의 진짜 책 정보 + 기존 이미지를 가져가는 API
    @GetMapping
    public Map<String, Object> getBookDetailWithImage(@PathVariable Long bookId) {
        BookEntity book = generateImageService.getBookById(bookId);

        GenerateImageResponseDto imageDto = null;
        try {
            imageDto = generateImageService.getImageByBookId(bookId);
        } catch (Exception e) {
            // 이미지가 아직 생성되지 않은 경우 예외 처리 방어
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", book.getId());
        response.put("title", book.getTitle());
        response.put("author", book.getAuthor());
        response.put("genre", book.getGenre());
        response.put("content", book.getContent());
        // 기존에 생성된 표지가 있다면 URL을 넣어주고, 없다면 빈 문자열
        response.put("coverImageUrl", imageDto != null ? imageDto.getCoverImageUrl() : "");

        return response;
    }

    // 🌟 2. AI 표지 생성 버튼을 눌렀을 때 작동하는 API
    @PostMapping("/generate")
    public GenerateImageResponseDto generateImage(
            @PathVariable Long bookId,
            @RequestBody GenerateImageCreateRequestDto dto
    ){
        return generateImageService.generateAndSaveImage(bookId, dto);
    }

    // 🌟 3. 수동 저장/수정 버튼을 눌렀을 때 작동하는 API
    @PostMapping("/image")
    public GenerateImageResponseDto saveOrUpdateImage(
            @PathVariable Long bookId,
            @RequestBody GenerateImageRequestDto dto
    ){
        return generateImageService.saveOrUpdateImage(bookId, dto);
    }
    //생성 이미지 저장하기 위함
    @PatchMapping
    public ResponseEntity<GenerateImageResponseDto> updateBookCover(
            @PathVariable("bookId") Long bookId,
            @RequestBody GenerateImageRequestDto dto
    ){
        GenerateImageResponseDto responseDto = generateImageService.saveOrUpdateImage(bookId, dto);
        return ResponseEntity.ok(responseDto);
    }
}