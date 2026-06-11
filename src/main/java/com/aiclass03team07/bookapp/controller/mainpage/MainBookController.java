package com.aiclass03team07.bookapp.controller.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.BookListDTO;
import com.aiclass03team07.bookapp.service.mainpage.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<BookListDTO> getAllBooks(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return mainPageService.getAllBooks(keyword, pageable);
    }
    @Operation(summary = "좋아요 랭킹", description = "좋아요 상위 N권 조회")
    @GetMapping("/ranking")
    public List<BookListDTO> getRanking(
            @RequestParam(defaultValue = "") String genre,
            @RequestParam(defaultValue = "10") int topN) {
        return mainPageService.getTopRanking(genre, topN);
    }

    @Operation(summary = "장르 목록", description = "등록된 도서의 전체 장르 목록")
    @GetMapping("/genres")
    public List<String> getGenres() {
        return mainPageService.getGenres();
    }
}