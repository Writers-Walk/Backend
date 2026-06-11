package com.aiclass03team07.bookapp.dto.review;

import lombok.Data;

@Data
public class SaveReviewDTO {

    private String content;
    private Long rating;
    private String user_id;
}
