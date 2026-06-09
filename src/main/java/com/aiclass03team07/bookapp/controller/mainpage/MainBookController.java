package com.aiclass03team07.bookapp.controller.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.service.mainpage.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Slf4j
public class MainBookController {

    private final MainPageService mainPageService;

    @Operation(summary = "도서 목록 조회", description = "전체 도서 목록 가져오기")
    @GetMapping("/getall")
    public List<BookListDTO> getAllBooks() {
        return mainPageService.getAllBooks();
    }
}