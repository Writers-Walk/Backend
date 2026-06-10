package com.aiclass03team07.bookapp.dto.generateimage;

import lombok.Data;

@Data
public class GenerateImageCreateRequestDto {
    private String userPrompt;      // 추가 입력 프롬프트
    private String imageModel;      // 화면에서 고른 모델명 (예: dall-e-3)
    private String resolution;      // 화면에서 고른 해상도 (예: 1024x1024)
    private String quality;         // 화면에서 고른 품질 (low/medium/high)
}
