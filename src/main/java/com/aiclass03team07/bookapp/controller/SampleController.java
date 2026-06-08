package com.aiclass03team07.bookapp.controller;

import com.aiclass03team07.bookapp.dto.SampleDTO;
import com.aiclass03team07.bookapp.dto.SaveDTO;
import com.aiclass03team07.bookapp.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sample")
@Slf4j
public class SampleController {

    private final SampleService sampleService;

    @Operation(summary = "모든 샘플 테이블 가져오기" , description = "모든 샘플 테이블 가져오기")
    @GetMapping("/getall")
    public List<SampleDTO> selectallsamples() {
        return sampleService.getallsamples();
    }

    @Operation(summary = "해당하는 id 데이터 가져오기" , description = "해당하는 id 데이터 가져오기")
    @PostMapping("/get/{id}")
    public ResponseEntity<SampleDTO> selectsample(@PathVariable Long id) {
        SampleDTO dto = sampleService.getsample(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "db 저장" , description = "db 저장")
    @PostMapping("/save")
    public ResponseEntity<SaveDTO> savesample(@RequestBody SaveDTO dto){
        sampleService.savesample(dto);
        return ResponseEntity.ok(dto);
    }


}
