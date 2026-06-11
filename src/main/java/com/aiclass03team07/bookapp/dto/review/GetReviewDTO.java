package com.aiclass03team07.bookapp.dto.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetReviewDTO {

    private String content; // 리뷰 내용
    private Long rating; //리뷰 평점
    private LocalDateTime createdAt; // 리뷰 생성일
    private String username; // 리뷰등록한 사람 이름
}
