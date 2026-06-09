package com.aiclass03team07.bookapp.controller.bookcreate;

import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.service.bookcreate.BookCreateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcreate")
@Slf4j
public class BookCreateController {
    private final BookCreateService bookCreateService;

    @Operation(summary = "book 생성 api", description = "book 생성 데이터 받고 저장")
    @PostMapping("/create")
    public ResponseEntity<BookCreateDTO> createbook (@RequestBody BookCreateDTO dto){
        bookCreateService.saveBookInfo(dto);
        return ResponseEntity.ok(dto);
    }

}
