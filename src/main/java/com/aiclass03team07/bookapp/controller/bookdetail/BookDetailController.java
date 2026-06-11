package com.aiclass03team07.bookapp.controller.bookdetail;

import com.aiclass03team07.bookapp.dto.bookdetail.DetailDTO;
import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.service.bookdetail.BookDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookdetail")
@Slf4j
public class BookDetailController {

    private final BookDetailService bookDetailService;


    @Operation(summary = "메인 페이지 도서 목록 조회", description = "메인 카드를 위한 도서 리스트와 찜 개수/여부 조회")
    @GetMapping("/books")
    public ResponseEntity<List<BookListDTO>> getMainBooks(@RequestParam(required = false) Long userId) {
        log.info("메인 도서 목록 요청 수신 - userId: {}", userId);
        List<BookListDTO> books = bookDetailService.getMainBookList(userId);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "책 정보 가져오는 api 책 정보 조회", description = "책 id 별 정보 조회")
    @GetMapping("/book/{id}")
    public DetailDTO getBookById(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        return bookDetailService.getBookDetail(id, userId);
    }

    @Operation(summary = "찜하기", description = "찜하기")
    @PostMapping("/book/{id}")
    public ResponseEntity<DetailDTO> toggleWish(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(bookDetailService.bookWishList(id, userId));
    }

    @Operation(summary = "도서삭제", description = "도서삭제")
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookDetailService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}