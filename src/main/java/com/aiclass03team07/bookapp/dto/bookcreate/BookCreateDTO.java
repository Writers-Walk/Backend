package com.aiclass03team07.bookapp.dto.bookcreate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookCreateDTO {
    //notblank -> 빈칸X
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")

    // builder 삭제 해야함.
    private String title; // 도서 제목

    @NotBlank(message = "저자는 필수입니다.")
    @Size(max = 50, message = "저자는 50자 이하로 입력해주세요.")
    private String author; // 저자

    @NotBlank(message = "출판사는 필수입니다.")
    private String publisher; // 출판사

    @Pattern(regexp = "^\\d{4}$", message = "발행년도는 4자리 숫자여야 합니다.")
    private String publishedDt; //발행년도

    @Size(max = 100, message = "시리즈 정보는 100자 이하로 입력해주세요.")
    private String seriesInfo; // 총서사항(몇권인지~ 시리즈 인지~)

    @Size(max = 50, message = "장르는 50자 이하로 입력해주세요.")
    private String genre; // 장르

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 1000, message = "내용은 1000자 이하로 입력해주세요.")
    private String content; // 상세 설명
}
