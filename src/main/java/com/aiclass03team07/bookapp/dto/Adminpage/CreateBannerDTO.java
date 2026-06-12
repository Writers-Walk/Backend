package com.aiclass03team07.bookapp.dto.Adminpage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateBannerDTO {

    @NotBlank(message = "배너 타입은 필수입니다.")
    @Pattern(regexp = "latestBanner|bestBanner", message = "latestBanner 또는 bestBanner만 가능합니다.")
    private String type; // latestBanner(신간 소개) / bestBanner(이주의 책)

    private String userPrompt;      // 추가 입력 프롬프트

    @NotBlank(message = "이미지 모델 선택은 필수입니다.")
    private String imageModel;

    @NotBlank(message = "해상도 선택은 필수입니다.")
    private String resolution;

    @NotBlank(message = "품질 선택은 필수입니다.")
    private String quality;         // low/medium/high
}