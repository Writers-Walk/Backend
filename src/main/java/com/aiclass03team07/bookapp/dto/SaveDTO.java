package com.aiclass03team07.bookapp.dto;

import lombok.Data;

@Data
public class SaveDTO {
    String title;
    String author;
    Long likes;
    String content;
    String genre;
    String publisher;

}
