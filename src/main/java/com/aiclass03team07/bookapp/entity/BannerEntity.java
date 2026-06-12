package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "cover_prompt", columnDefinition = "TEXT")
    private String coverPrompt;

    @Column(name = "image_url", columnDefinition = "CLOB")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}