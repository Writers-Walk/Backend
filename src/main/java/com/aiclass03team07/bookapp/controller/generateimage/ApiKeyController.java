package com.aiclass03team07.bookapp.controller.generateimage;

import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config") // 🌟 꼬일 일이 없는 깔끔하고 독립된 주소 영역
@RequiredArgsConstructor
public class ApiKeyController {
    private final GenerateImageService generateImageService;

    @GetMapping("/api-key")
    public Map<String, String> getApiKey() {
        Map<String, String> response = new HashMap<>();
        response.put("apiKey", generateImageService.getApiKeyForClient());
        return response;
    }
}
