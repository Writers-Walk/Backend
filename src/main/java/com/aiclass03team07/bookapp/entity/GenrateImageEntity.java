package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GenrateImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coverImageUrl")
    private String coverImageUrl;

    @Column(name = "imageModel")
    private String imageModel;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "quality")
    private String quality;

    @Column(name = "coverPrompt")
    private String coverPrompt;

    @OneToOne(mappedBy = "generateImageEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BookEntity bookEntity;
}
