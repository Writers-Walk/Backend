package com.aiclass03team07.bookapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BannerImageUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "latest_banner")
    private String latestBanner;

    @Lob
    @Column(name = "best_banner")
    private String bestBanner;

    @Lob
    @Column(name = "ai_recommend_banner")
    private String aiRecommendBanner;
}
