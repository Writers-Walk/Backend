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

    @Column(name = "latest_banner")
    private String latestBanner;

    @Column(name = "best_banner")
    private String bestBanner;

    @Column(name = "ai_recommend_banner")
    private String aiRecommendBanner;
}
