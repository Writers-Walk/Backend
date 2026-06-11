package com.aiclass03team07.bookapp.service.mainpage;


import com.aiclass03team07.bookapp.dto.mainpage.MainBannerDTO;
import com.aiclass03team07.bookapp.entity.BannerImageUrlEntity;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BannerImageUrlRepository;
import com.aiclass03team07.bookapp.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainBannerService {
    private  final BookRepository bookRepository;
    private final BannerImageUrlRepository bannerImageUrlRepository;


    //배너 이미지 3개 가져오는
    public MainBannerDTO selectBanners(){
        return bannerImageUrlRepository.findById(1L).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Banner not found"));
    }

    public BookEntity getLatestBook() {
        // 0페이지에서 1개만 가져오되, createdAt 기준으로 내림차순 정렬
        Page<BookEntity> page = bookRepository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return page.hasContent() ? page.getContent().get(0) : null;
    }

    //관리자 계정일때 배너 생성하는


//    public BookEntity getMostLikedBook() {
//        return bookRepository.findTopByOrderByLikesDesc();
//    }

    private MainBannerDTO convertToDTO (BannerImageUrlEntity entity){
        MainBannerDTO dto = new MainBannerDTO();
        dto.setLatesturl(entity.getLatestBanner());
        dto.setBesturl(entity.getBestBanner());
        dto.setAiRecommendurl(entity.getAiRecommendBanner());

        return dto;
    }
}
