package com.aiclass03team07.bookapp.controller.generateimage;

import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books/{bookId}/image")
@RequiredArgsConstructor
public class GenerateImageController {

    private final GenerateImageService generateImageService;

    @PostMapping
    public GenerateImageResponseDto saveOrUpdateImage(
            @PathVariable Long bookId,
            @RequestBody GenerateImageRequestDto dto
    ){
        return generateImageService.saveOrUpdateImage(bookId, dto);
    }

    @GetMapping
    public GenerateImageResponseDto getImageByBookId(@PathVariable Long bookId){
        return generateImageService.getImageByBookId(bookId);
    }

    @PostMapping("/generate")
    public GenerateImageResponseDto generateImage(
            @PathVariable Long bookId,
            @RequestBody GenerateImageCreateRequestDto dto
    ){
        return generateImageService.generateAndSaveImage(bookId, dto);
    }
}
