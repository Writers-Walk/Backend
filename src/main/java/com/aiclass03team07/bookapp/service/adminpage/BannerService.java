//package com.aiclass03team07.bookapp.service.adminpage;
//
//import com.aiclass03team07.bookapp.dto.Adminpage.CreateBannerDTO;
//import com.aiclass03team07.bookapp.entity.BannerEntity;
//import com.aiclass03team07.bookapp.entity.BookEntity;
//import com.aiclass03team07.bookapp.repository.BannerRepository;
//import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
//import com.aiclass03team07.bookapp.service.mainpage.MainBannerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class BannerService {
//
//    private final BannerRepository bannerRepository;
//    private final MainBannerService mainBannerService;
//    private final GenerateImageService generateImageService;
//
//    private static final String DEFAULT_MODEL = "gpt-image-2";
//    private static final String DEFAULT_SIZE = "1024x1024";
//    private static final String DEFAULT_QUALITY = "medium";
//
//    @Transactional
//    public BannerEntity createBanner(CreateBannerDTO dto) {
//
//        // 1. type에 따라 대상 책 선택
//        BookEntity targetBook = switch (dto.getType()) {
//            case "latestBanner" -> mainBannerService.getLatestBook();      // 신간 소개
//            case "bestBanner" -> mainBannerService.getMostWishedBook();    // 이주의 책
//            default -> throw new IllegalArgumentException("잘못된 배너 타입: " + dto.getType());
//        };
//
//        if (targetBook == null) {
//            throw new RuntimeException("배너에 사용할 도서를 찾을 수 없습니다.");
//        }
//
//        // 2. 옵션값 확정 (없으면 기본값)
//        String model = resolve(dto.getImageModel(), DEFAULT_MODEL);
//        String size = resolve(dto.getResolution(), DEFAULT_SIZE);
//        String quality = resolve(dto.getQuality(), DEFAULT_QUALITY);
//
//        // 3. 프롬프트 구성 (책 정보 + 사용자 추가 입력)
//        String prompt = buildPrompt(dto, targetBook);
//
//        // 4. 이미지 생성 (GenerateImageService 재사용) ✅
//        String base64Image = generateImageService.callOpenAiImageApi(prompt, model, size, quality);
//        String imageUrl = "data:image/png;base64," + base64Image;
//
//        // 5. 배너 저장 (같은 type 있으면 덮어쓰기)
//        BannerEntity banner = bannerRepository.findTopByTypeOrderByCreatedAtDesc(dto.getType())
//                .orElse(BannerEntity.builder().type(dto.getType()).build());
//
//        banner.setBookId(targetBook.getId());
//        banner.setCoverPrompt(prompt);
//        banner.setImageUrl(imageUrl);
//
//        return bannerRepository.save(banner);
//    }
//
//    private String resolve(String value, String defaultValue) {
//        return (value != null && !value.isBlank()) ? value : defaultValue;
//    }
//
//    private String buildPrompt(CreateBannerDTO dto, BookEntity book) {
//        StringBuilder sb = new StringBuilder();
//
//        if ("latestBanner".equals(dto.getType())) {
//            sb.append("신간 소개 배너 이미지. ");
//        } else {
//            sb.append("이주의 책 추천 배너 이미지. ");
//        }
//
//        sb.append("도서 제목: ").append(book.getTitle()).append(", ");
//        sb.append("저자: ").append(book.getAuthor()).append(", ");
//        if (book.getGenre() != null) {
//            sb.append("장르: ").append(book.getGenre()).append(", ");
//        }
//
//        if (dto.getUserPrompt() != null && !dto.getUserPrompt().isBlank()) {
//            sb.append("추가 요청사항: ").append(dto.getUserPrompt());
//        }
//
//        return sb.toString();
//    }
//}