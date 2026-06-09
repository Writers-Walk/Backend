package com.aiclass03team07.bookapp.dto.generateimage;

import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenerateImageResponseDto {

    private Long id;
    private String coverImageUrl;
    private String imageModel;
    private String resolution;
    private String quality;
    private String coverPrompt;

    public static GenerateImageResponseDto from(GenerateImageEntity entity){
        if(entity == null){
            return null;
        }

        return GenerateImageResponseDto.builder()
                .id(entity.getId())
                .coverImageUrl(entity.getCoverImageUrl())
                .imageModel(entity.getImageModel())
                .resolution(entity.getResolution())
                .quality(entity.getQuality())
                .coverPrompt(entity.getCoverPrompt())
                .build();
    }
}
