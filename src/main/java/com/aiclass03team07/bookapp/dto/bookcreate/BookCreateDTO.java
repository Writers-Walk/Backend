package com.aiclass03team07.bookapp.dto.bookcreate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCreateDTO {

    private String title; // 도서 제목
    private String author; // 저자
    private String publisher; // 출판사
    private String publishedDt; //발행년도
    private String seriesInfo; // 총서사항(몇권인지~ 시리즈 인지~)
    private String isbn; // ISBN
    private String genre; // 장르
    private String content; // 상세 설명
}
