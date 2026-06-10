package com.aiclass03team07.bookapp.controller;


import com.aiclass03team07.bookapp.service.bookdetail.DetailService;
import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.entity.BookDetail;
import com.aiclass03team07.bookapp.service.DetailService;
import com.aiclass03team07.bookapp.service.bookdetail.BookDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookdetail")
@Slf4j
public class BookDetailController {

    private final BookDetailService bookDetailService;

//    @GetMapping("/books/{id}")
//    public BookDetail getBook(@PathVariable Long id) {
//        return detailService.findById(id);
//    }
//
//    @DeleteMapping("/books/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//        detailService.deleteBook(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/books/likescount")
//    public long getCount() {
//        return detailService.count();
//    }
//
//    @PostMapping("/books/{id}/like")
//    public ResponseEntity<Void> addLike(@PathVariable Long id) {
//        detailService.updateLikes(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/books/{id}/likescount")
//    public Long getLikesCount(@PathVariable Long id){
//        return detailService.getLikesCount(id);
//    }
//
//    @PatchMapping("/books/{id}")
//    public  ResponseEntity<Void> updateLikes(@PathVariable Long id) {
//        detailService.updateLikes(id);
//        return ResponseEntity.ok().build();
//    }

    @Operation(summary = "좋아요 + 1", description = "좋아요증가")
    @PostMapping("/book/{id}")
    public ResponseEntity<BookCreateDTO> updateLikes(@PathVariable Long id) {
        return ResponseEntity.ok(bookDetailService.updateLikes(id));
    }


}




