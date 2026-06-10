package com.aiclass03team07.bookapp.controller;

import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.entity.BookDetail;
import com.aiclass03team07.bookapp.service.DetailService;
import com.aiclass03team07.bookapp.service.bookdetail.BookDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookdetail")
@Slf4j
public class BookDetailController {

    private final BookDetailService bookDetailService;

    @Operation(summary = "좋아요 + 1", description = "좋아요증가")
    @PostMapping("/book/{id}")
    public ResponseEntity<BookCreateDTO> updateLikes(@PathVariable Long id) {
        return ResponseEntity.ok(bookDetailService.updateLikes(id));
    }

}




