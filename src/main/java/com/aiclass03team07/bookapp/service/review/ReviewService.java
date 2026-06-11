package com.aiclass03team07.bookapp.service.review;

import com.aiclass03team07.bookapp.dto.review.GetReviewDTO;
import com.aiclass03team07.bookapp.dto.review.SaveReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    public List<GetReviewDTO> selectReviewAll(Long id){
        List<GetReviewDTO> dto = new ArrayList<>();

        return dto;
    }

    public SaveReviewDTO saveReview (SaveReviewDTO dto){
        SaveReviewDTO dtos = new SaveReviewDTO();

        return dtos;
    }
}
