package com.aiclass03team07.bookapp.service;

import com.aiclass03team07.bookapp.dto.SampleDTO;
import com.aiclass03team07.bookapp.dto.SaveDTO;
import com.aiclass03team07.bookapp.entity.SampleEntity;
import com.aiclass03team07.bookapp.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    //모든 sample가져오기
    public List<SampleDTO> getallsamples() {
        return sampleRepository.findAll().stream().map(this::convertToSampleDTO).collect(Collectors.toList());

    }


    // id 조회
    public SampleDTO getsample(Long id){
        SampleDTO dto = new SampleDTO();

        SampleEntity entity = sampleRepository.findById(id).get();

        dto.setId(id);
        dto.setSampletext(entity.getSampletext());

        return dto;
    }

    //db 저장
    public void savesample(SaveDTO dto){
        SampleEntity entity = convertToSampleEntity(dto);
        sampleRepository.save(entity);
    }

    //dto -> entity convert
    private SampleEntity convertToSampleEntity(SaveDTO dto){
        SampleEntity entity = new SampleEntity();
        entity.setSampletext(dto.getSampletext());

        return entity;
    }

    //entity -> dto convert
    private SampleDTO convertToSampleDTO(SampleEntity entity) {
        SampleDTO dto = new SampleDTO();

        dto.setId(entity.getId());
        dto.setSampletext(entity.getSampletext());

        return dto;
    }

}
