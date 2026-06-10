package com.aiclass03team07.bookapp.service.generateimage;

import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.GenerateImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerateImageService {

    private final BookRepository bookRepository;
    private final GenerateImageRepository generateImageRepository;

    @Value("${api.key}")
    private String apiKey;
    public String getApiKeyForClient() {
        return this.apiKey;
    }
    ///
    public BookEntity getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다. id = " + bookId));
    }
    ///

    public GenerateImageResponseDto saveOrUpdateImage(Long bookId, GenerateImageRequestDto dto){

        // 1. 책 찾기
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다. id = " + bookId));

        // 2. 기존 이미지 있으면 가져오고, 없으면 새로 생성
        GenerateImageEntity image = book.getGenerateImageEntity();

        if(image == null){
            image = new GenerateImageEntity();
        }

        // 3. DTO 값 넣기
        image.setCoverImageUrl(dto.getCoverImageUrl());
        image.setImageModel(dto.getImageModel());
        image.setResolution(dto.getResolution());
        image.setQuality(dto.getQuality());
        image.setCoverPrompt(dto.getCoverPrompt());

        // 4. 이미지 저장
        GenerateImageEntity savedImage = generateImageRepository.save(image);

        // 5. 책에 이미지 연결
        book.setGenerateImageEntity(savedImage);
        bookRepository.save(book);

        // 6. 응답 DTO 반환
        return GenerateImageResponseDto.from(savedImage);
    }

    public GenerateImageResponseDto getImageByBookId(Long bookId){
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다. id = " + bookId));

        return GenerateImageResponseDto.from(book.getGenerateImageEntity());
    }

    public GenerateImageResponseDto generateAndSaveImage(Long bookId, GenerateImageCreateRequestDto dto){
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다. id = " + bookId));

        String prompt = createPrompt(book, dto.getUserPrompt());

        String base64Image = callOpenAiImageApi(prompt, dto.getImageModel(), dto.getResolution(), dto.getQuality());

        String imageUrl = "data:image/png;base64," + base64Image;

        GenerateImageEntity image = book.getGenerateImageEntity();
        if(image == null){
            image = new GenerateImageEntity();
        }

        image.setCoverImageUrl(imageUrl);
//        image.setImageModel("gpt-image-2");
//        image.setResolution("1024x1024");
//        image.setQuality("medium");
        image.setImageModel(dto.getImageModel() != null ? dto.getImageModel() : "dall-e-3");
        image.setResolution(dto.getResolution() != null ? dto.getResolution() : "1024x1024");
        image.setQuality(dto.getQuality() != null ? dto.getQuality() : "medium");
        image.setCoverPrompt(prompt);

        GenerateImageEntity savedImage = generateImageRepository.save(image);

        book.setGenerateImageEntity(savedImage);
        bookRepository.save(book);

        return GenerateImageResponseDto.from(savedImage);
    }

    private String createPrompt(BookEntity book, String userPrompt){
        return """
                책 제목: %s
                저자: %s
                장르: %s
                책 내용: %s

                위 도서 정보를 바탕으로 책 표지 이미지를 생성하세요.
                표지는 책의 분위기와 잘 어울리게 시각적으로 매력적으로 구성하세요.

                사용자 추가 요구사항:
                %s
                """.formatted(
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getContent(),
                userPrompt == null ? "없음" : userPrompt
        );
    }

    private String callOpenAiImageApi(String prompt, String imageModel, String resolution, String quality){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String finalModel = (imageModel != null && !imageModel.isBlank()) ? imageModel : "gpt-image-2";
        String finalSize = (resolution != null && !resolution.isBlank()) ? resolution : "1024x1024";
        String finalQuality = (quality != null && !quality.isBlank()) ? quality : "medium";

        Map<String, Object> body = Map.of(
                "model", finalModel,
                "prompt", prompt,
                "size", finalSize,
                "quality", finalQuality,
                "n", 1
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.openai.com/v1/images/generations",
                HttpMethod.POST,
                request,
                Map.class
        );

        if(response.getBody() == null){
            throw new RuntimeException("이미지 생성 응답 body가 비어 있습니다.");
        }

        List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

        if(data == null || data.isEmpty()){
            throw new RuntimeException("이미지 생성 응답 data가 비어 있습니다.");
        }

        String base64Image = (String) data.get(0).get("b64_json");

        if(base64Image == null || base64Image.isBlank()){
            throw new RuntimeException("이미지 생성 결과 b64_json이 비어 있습니다.");
        }

        return base64Image;
    }
}
