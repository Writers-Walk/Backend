package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Long rating;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    //여기 책 과 1대1 연결
    @OneToOne
    @JoinColumn(name = "generateImage_id")
    private GenerateImageEntity generateImageEntity;

    //여기 user랑 1대n 연결

}
