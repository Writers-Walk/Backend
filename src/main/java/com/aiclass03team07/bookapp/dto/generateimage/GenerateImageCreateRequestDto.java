package com.aiclass03team07.bookapp.dto.generateimage;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateImageCreateRequestDto {
    private String userPrompt;      // 추가 입력 프롬프트

    @NotBlank(message = "이미지 모델 선택은 필수입니다.")    //예외처리
    private String imageModel;    // 화면에서 고른 모델명 (예: dall-e-3)

    @NotBlank(message = "해상도 선택은 필수입니다.")   //예외처리
    private String resolution;    // 화면에서 고른 해상도 (예: 1024x1024)

    @NotBlank(message = "품질 선택은 필수입니다.")    //예외처리
    private String quality;         // 화면에서 고른 품질 (low/medium/high)
}
