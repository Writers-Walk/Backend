package com.aiclass03team07.bookapp.controller.mainpage;

import com.aiclass03team07.bookapp.dto.mainpage.MainBannerDTO;
import com.aiclass03team07.bookapp.service.mainpage.MainBannerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banner")
@Slf4j
public class MainBannerController {
    private final MainBannerService mainBannerService;

    @Operation(summary = "배너 이미지 3개 가져오는 api" , description = "각각 이미지 하나씩 경로 가져옴")
    @GetMapping("/get")
    public MainBannerDTO getBannerurl(){
        return mainBannerService.selectBanners();
    }

    @Operation(summary = "관리자가 요청하면 광고 배너 생성 api", description = "['신간 소개', '이 주의 책'] 둘중 하나 선택해서 같이 보내줘야함 그러고 db에 이미지 생성 ")
    @PostMapping("/createbanner")
    public
    //    @Operation(summary = "신간 책 광고", description = "가장 최근에 등록된 책")
//    @GetMapping("/newbook")
//    public MainBannerDTO getLatestBook(){
//        return mainBannerService.getLatestBook();
//    }
//
//    @Operation(summary = "이번 주 베스트 책", description = "좋아요 가장 많은 책")
//    @GetMapping("/bestbook")
//    public BookEntity getWeeklyBestBook() {
//        return mainBannerService.getMostLikedBook();
//    }
}
