package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GenerateImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "coverImageUrl", columnDefinition = "LONGTEXT")
    private String coverImageUrl;

    @Column(name = "imageModel")
    private String imageModel;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "quality")
    private String quality;

    @Column(name = "coverPrompt", length = 5000)
    private String coverPrompt;

    @OneToOne(mappedBy ="generateImageEntity", fetch = FetchType.LAZY)
    private BookEntity bookEntity;
}
