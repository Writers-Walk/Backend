package com.aiclass03team07.bookapp.dto.mainpage;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookListDTO {

    private Long id;
    private String title;
    private String author;
    private Long likes;
    private String genre;
    private String coverImageUrl;   // GenerateImage에서 가져옴
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}