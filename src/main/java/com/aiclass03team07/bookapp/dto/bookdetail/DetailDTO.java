package com.aiclass03team07.bookapp.dto.bookdetail;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailDTO {
    private String title;
    private String author;
    private Long likes;
    private String content;
    private String genre;
    private String publisher;
    private String coverImageUrl;
    private String seriesInfo;
    private String publicationDt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
