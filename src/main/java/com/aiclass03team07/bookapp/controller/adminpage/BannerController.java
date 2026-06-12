package com.aiclass03team07.bookapp.controller.adminpage;

import com.aiclass03team07.bookapp.dto.Adminpage.CreateBannerDTO;
import com.aiclass03team07.bookapp.entity.BannerEntity;
import com.aiclass03team07.bookapp.service.adminpage.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/banner")
public class BannerController {

    private final BannerService bannerService;

    @Operation(summary = "광고 배너 이미지 생성", description = "신간소개/이주의책 배너 이미지를 AI로 생성")
    @PostMapping
    public ResponseEntity<BannerEntity> createBanner(@Valid @RequestBody CreateBannerDTO dto) {
        return ResponseEntity.ok(bannerService.createBanner(dto));
    }
}