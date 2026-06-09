package com.aiclass03team07.bookapp.dto.generateimage;

import lombok.Data;

@Data
public class GenerateImageRequestDto {

    private String coverImageUrl;
    private String imageModel;
    private String resolution;
    private String quality;
    private String coverPrompt;
}
