package com.aiclass03team07.bookapp.controller;

import com.aiclass03team07.bookapp.service.bookdetail.DetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookdetail")
@Slf4j
public class BookDetailController {

    private final DetailService detailService;

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

}




