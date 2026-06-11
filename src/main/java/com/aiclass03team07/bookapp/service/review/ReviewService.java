package com.aiclass03team07.bookapp.service.review;

import com.aiclass03team07.bookapp.dto.review.GetReviewDTO;
import com.aiclass03team07.bookapp.dto.review.SaveReviewDTO;
import com.aiclass03team07.bookapp.entity.ReviewEntity;
import com.aiclass03team07.bookapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 책 id 받아서 리뷰 조회
    public List<GetReviewDTO> selectReviewAll(Long id){
        List<GetReviewDTO> dto = new ArrayList<>();


        return dto;
    }

    public SaveReviewDTO saveReview (SaveReviewDTO dto){
        SaveReviewDTO dtos = new SaveReviewDTO();

        return dtos;
    }

    //entity -> dto
    private GetReviewDTO converToDTO(ReviewEntity entity){
        GetReviewDTO dto = new GetReviewDTO();

        dto.setContent(entity.getContent());
    }
}
