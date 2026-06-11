package com.aiclass03team07.bookapp.controller.review;

import com.aiclass03team07.bookapp.dto.review.GetReviewDTO;
import com.aiclass03team07.bookapp.dto.review.SaveReviewDTO;
import com.aiclass03team07.bookapp.service.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "해당 책의 리뷰 데이터 모두 가져오는 api", description = "책id 받아서 그 해당 책들의 리뷰를 다 가져옴")
    @GetMapping("/getall")
    public List<GetReviewDTO> getReviewAll(@PathVariable Long id){
        return reviewService.selectReviewAll(id);
    }

    @Operation(summary = "해당 책의 리뷰 저장하는 api", description = "리뷰에 저장할 데이터 받아서 저장")
    @PostMapping("/getall")
    public ResponseEntity<SaveReviewDTO> saveReview(@RequestBody SaveReviewDTO dto){
        reviewService.saveReview(dto);
        return ResponseEntity.ok(dto);
    }


}
