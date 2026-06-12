package com.aiclass03team07.bookapp.service.generateimage;

import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageCreateRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageRequestDto;
import com.aiclass03team07.bookapp.dto.generateimage.GenerateImageResponseDto;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.GenerateImageEntity;
import com.aiclass03team07.bookapp.exception.BookNotFoundException;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.GenerateImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerateImageService {

    private final BookRepository bookRepository;
    private final GenerateImageRepository generateImageRepository;
    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

//    public String getApiKeyForClient() {
//        return this.apiKey;
//    }

    // 기본값 관리
    private static final String DEFAULT_MODEL = "gpt-image-2";
    private static final String DEFAULT_SIZE = "1024x1024";
    private static final String DEFAULT_QUALITY = "medium";

    // ──────────────────────────────────────────────
    // 조회
    // ──────────────────────────────────────────────

    @Transactional(readOnly = true)
    public BookEntity getBookById(Long bookId) {
        return findBookOrThrow(bookId);
    }

    @Transactional(readOnly = true)
    public GenerateImageResponseDto getImageByBookId(Long bookId){
        BookEntity book = findBookOrThrow(bookId);
        return GenerateImageResponseDto.from(book.getGenerateImageEntity());
    }

    // ──────────────────────────────────────────────
    // 수동 저장/수정
    // ──────────────────────────────────────────────

    @Transactional
    public GenerateImageResponseDto saveOrUpdateImage(Long bookId, GenerateImageRequestDto dto){

        BookEntity book = findBookOrThrow(bookId);
        return persist(
                book,
                dto.getCoverImageUrl(),
                dto.getImageModel(),
                dto.getResolution(),
                dto.getQuality(),
                dto.getCoverPrompt()
        );
    }

    // ──────────────────────────────────────────────
    // AI 표지 생성 + 저장
    // ──────────────────────────────────────────────

    public GenerateImageResponseDto generateAndSaveImage(Long bookId, GenerateImageCreateRequestDto dto){
        BookEntity book = findBookOrThrow(bookId);

        // 옵션값 확정
        String model = resolve(dto.getImageModel(), DEFAULT_MODEL);
        String size = resolve(dto.getResolution(), DEFAULT_SIZE);
        String quality = resolve(dto.getQuality(), DEFAULT_QUALITY);

        // 프롬프트 생성
        String prompt = createPrompt(book, dto.getUserPrompt());

        // 외부 API 호출
        String base64Image = callOpenAiImageApi(prompt, model, size, quality);
        String imageUrl = "data:image/png;base64," + base64Image;

        // 저장
        return persist(book, imageUrl, model, size, quality, prompt);
    }

    // ──────────────────────────────────────────────
    // 공통 저장 로직 (private)
    // saveOrUpdateImage(@Transactional) 안에서 호출되면 트랜잭션이 적용
    // ──────────────────────────────────────────────

    private GenerateImageResponseDto persist(BookEntity book,
                                             String coverImageUrl,
                                             String imageModel,
                                             String resolution,
                                             String quality,
                                             String coverPrompt){
        GenerateImageEntity image = book.getGenerateImageEntity();
        if(image == null){
            image = new GenerateImageEntity();
        }

        image.setCoverImageUrl(coverImageUrl);
        image.setImageModel(imageModel);
        image.setResolution(resolution);
        image.setQuality(quality);
        image.setCoverPrompt(coverPrompt);

        GenerateImageEntity savedImage = generateImageRepository.save(image);

        book.setGenerateImageEntity(savedImage);
        bookRepository.save(book);

        return GenerateImageResponseDto.from(savedImage);
    }

    // ──────────────────────────────────────────────
    // 헬퍼
    // ──────────────────────────────────────────────

    private BookEntity findBookOrThrow(Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private String resolve(String value, String defaultValue){
        return (value != null && !value.isBlank()) ? value : defaultValue;
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


    public String callOpenAiImageApi(String prompt, String model, String size, String quality){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", model,
                "prompt", prompt,
                "size", size,
                "quality", quality,
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

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

        if(data == null || data.isEmpty()){
            throw new RuntimeException("이미지 생성 응답 data가 비어 있습니다.");
        }

        // gpt-image 항상 base64로 반환
        String base64Image = (String) data.get(0).get("b64_json");

        if(base64Image == null || base64Image.isBlank()){
            throw new RuntimeException("이미지 생성 결과 b64_json이 비어 있습니다.");
        }

        return base64Image;
    }
}
