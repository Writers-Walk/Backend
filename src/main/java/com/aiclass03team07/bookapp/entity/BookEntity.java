package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "저자는 필수입니다.")
    @Size(max = 50, message = "저자는 50자 이하로 입력해주세요.")
    @Column(name = "author")
    private String author;

//    @Column(name = "likes")  좋아요 삭제
//    private Long likes;

    //@NotBlank(message = "내용은 필수입니다.")
    @Size(max = 1000, message = "내용은 1000자 이하로 입력해주세요.")
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "content")
    private String content;

    @Column(name = "genre")
    private String genre;

    @NotBlank(message = "출판사는 필수입니다.")
    @Size(max = 50, message = "출판사는 50자 이하로 입력해주세요.")
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "seriesinfo")
    private String seriesInfo;


    @Pattern(
            regexp = "^\\d{4}$",
            message = "발행년도는 4자리 숫자여야 합니다."
    )
    @Column(name = "publishedDt")
    private String publishedDt;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "generateImage_id")
    private GenerateImageEntity generateImageEntity;

    @OneToMany(mappedBy = "bookEntity", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntities;
}
