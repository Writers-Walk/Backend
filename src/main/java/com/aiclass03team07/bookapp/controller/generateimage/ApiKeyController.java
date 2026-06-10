//package com.aiclass03team07.bookapp.controller.generateimage;
//
//import com.aiclass03team07.bookapp.service.generateimage.GenerateImageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//// 서버에 기본 OpenAI 키가 설정돼 있는지 '여부'만 알려주는 엔드포인트
//// 키 값 자체는 절대 반환하지 않음
//
//@RestController
//@RequestMapping("/api/config") // 🌟 꼬일 일이 없는 깔끔하고 독립된 주소 영역
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
//public class ApiKeyController {
//    private final GenerateImageService generateImageService;
//
//    @GetMapping("/api-key-status")
//    public Map<String, Boolean> getApiKeyStatus(){
//        return Map.of("hasDefaultKey", generateImageService.hasDefaultKey());
//    }
//
////    public Map<String, String> getApiKey() {
////        Map<String, String> response = new HashMap<>();
////        response.put("apiKey", generateImageService.getApiKeyForClient());
////        return response;
////    }
//}
