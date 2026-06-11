package com.aiclass03team07.bookapp.service.mainpage;


import com.aiclass03team07.bookapp.dto.mainpage.CreateBannerDTO;
import com.aiclass03team07.bookapp.dto.mainpage.MainBannerDTO;
import com.aiclass03team07.bookapp.entity.BannerImageUrlEntity;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.WishListEntity;
import com.aiclass03team07.bookapp.repository.BannerImageUrlRepository;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.GenerateImageRepository;
import com.aiclass03team07.bookapp.repository.WishlistRepository;
import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainBannerService {
    private final BookRepository bookRepository;
    private final BannerImageUrlRepository bannerImageUrlRepository;
    private final GenerateImageService generateImageService;
    private final WishlistRepository wishlistRepository;
    private final GenerateImageRepository generateImageRepository;

    private static final String DEFAULT_MODEL = "gpt-image-2";
    private static final String DEFAULT_SIZE = "3840x1536";
    private static final String DEFAULT_QUALITY = "medium";


    //배너 이미지 3개 가져오는
    public MainBannerDTO selectBanners(){
        return bannerImageUrlRepository.findById(1L).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Banner not found"));
    }

    //관리자 계정일때 배너 생성하는
    public void makeBanner(CreateBannerDTO dto){

        BookEntity bookentity = findtypebook(dto.getType());
        boolean isexist = isUrlExistence(bookentity);

        if(isexist == true) {
            BannerImageUrlEntity urlentity = bannerImageUrlRepository.findById(1L)
                    .orElse(new BannerImageUrlEntity());

            urlentity.setId(1L);

            if (dto.getType().equals("latestBanner")) {
                String img_url = generateAndSaveImage(bookentity, dto);
                urlentity.setLatestBanner(img_url);
            } else {
                String img_url = generateAndSaveImage(bookentity, dto);
                urlentity.setBestBanner(img_url);
            }

            bannerImageUrlRepository.save(urlentity);
        }
        else
            return;
    }

    // api 이미 생성 후 url 반환
    private String generateAndSaveImage(BookEntity bookentity, CreateBannerDTO dto){
        String coverImageUrl = bookentity.getGenerateImageEntity().getCoverImageUrl();
        String type = new String();
        if(dto.getType().equals("latestBanner")){
            type = "신간 소개";
        }
        else if(dto.getType().equals("bestBanner")){
            type = "이 주의 책";
        }

        // 옵션값 확정
        String model = resolve(dto.getImageModel(), DEFAULT_MODEL);
        String size = resolve(dto.getResolution(), DEFAULT_SIZE);
        String quality = resolve(dto.getQuality(), DEFAULT_QUALITY);

        // 프롬프트 생성
        String prompt = createPrompt(bookentity, coverImageUrl, dto.getType(), dto.getUserPrompt());

        // 외부 API 호출
        String base64Image = generateImageService.callOpenAiImageApi(prompt, model, size, quality);

        // 저장
        return "data:image/png;base64," + base64Image;
    }

    private String resolve(String value, String defaultValue){
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    private String createPrompt(BookEntity book, String cover_img,String type,String userPrompt){
        return """
                책 제목: %s
                저자: %s
                장르: %s
                책 내용: %s
                책 표지: %s
                광고 문구: %s
                
                다음 항목들을 따라서 이미지를 생성하세요.
                1. 위 도서 정보를 바탕으로 광고 배너 이미지를 생성하세요.
                2. 이미지의 크기는 3840x1536 으로 생성하세요.
                3. 어울리는 광고 문구를 같이 넣으세요.
                4. 책 표지는 그대로 같이 광고 배너에 넣으세요.
                5. 광고 문구도 그대로 같이 이미지에 넣고 광고 느낌이 나게 넣으세요.
                6. 사용자 추가 요구사항을 같이 반영해서 이미지를 만드세요.

                사용자 추가 요구사항:
                %s
                """.formatted(
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getContent(),
                cover_img,
                type,
                userPrompt == null ? "없음" : userPrompt
        );
    }

    //latestBanner 이거나 bestBanner에 맞는 book entity 찾기
    private BookEntity findtypebook(String type){
        if(type.equals("latestBanner")){
            Page<BookEntity> page = bookRepository.findAll(
                    PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdAt"))
            );
            return page.hasContent() ? page.getContent().get(0) : null;
        }

        else if(type.equals("bestBanner")){
            return culwhish();
        }
        return null;
    }

    private BookEntity culwhish(){
        List<WishListEntity> wishList = wishlistRepository.findAll();
        Map<Long, Long> bookWishCount = wishList.stream()
                .collect(Collectors.groupingBy(WishListEntity::getBookId, Collectors.counting()));

        Long bestBookId = bookWishCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return bookRepository.findById(bestBookId)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));
    }

    // 이미지 존재 검사
    private boolean isUrlExistence(BookEntity entity){
        String coverImageUrl = entity.getGenerateImageEntity().getCoverImageUrl();
        return coverImageUrl != null && !coverImageUrl.isEmpty();
    }
    //entity -> dto
    private MainBannerDTO convertToDTO (BannerImageUrlEntity entity){
        MainBannerDTO dto = new MainBannerDTO();
        dto.setLatesturl(entity.getLatestBanner());
        dto.setBesturl(entity.getBestBanner());
        dto.setAiRecommendurl(entity.getAiRecommendBanner());

        return dto;
    }
}
