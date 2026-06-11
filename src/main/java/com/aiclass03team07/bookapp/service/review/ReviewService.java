package com.aiclass03team07.bookapp.service.review;

import com.aiclass03team07.bookapp.dto.review.GetReviewDTO;
import com.aiclass03team07.bookapp.dto.review.SaveReviewDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.entity.ReviewEntity;
import com.aiclass03team07.bookapp.entity.UserEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import com.aiclass03team07.bookapp.repository.ReviewRepository;
import com.aiclass03team07.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 책 id 받아서 리뷰 조회
    public List<GetReviewDTO> selectReviewAll(Long book_id){
        List<GetReviewDTO> dtos = new ArrayList<GetReviewDTO>();
        dtos = reviewRepository.findAllByBookEntityId(book_id).stream().map(entity ->{
            String user_name = entity.getUserentity().getUserId();
            return converToDTO(entity,user_name);
        }).collect(Collectors.toList());
        return dtos;
    }

    //책 id + 저장할리뷰 저장
    public SaveReviewDTO saveReview (Long book_id, SaveReviewDTO dto){
        ReviewEntity entity = new ReviewEntity();
        BookEntity bookEntity = bookRepository.findById(book_id)
                .orElseThrow(() -> new RuntimeException("책 없음"));
        entity.setBookEntity(bookEntity);
        entity.setRating(dto.getRating());
        entity.setContent(dto.getContent());
        entity.setBookEntity(bookEntity);
        UserEntity user = userRepository.findByUserId(dto.getUser_id())
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        entity.setUserentity(user);
        reviewRepository.save(entity);

        return dto;
    }

    //entity -> dto (+ username 까지)
    private GetReviewDTO converToDTO(ReviewEntity entity, String user_name){
        GetReviewDTO dto = new GetReviewDTO();
        dto.setContent(entity.getContent());
        dto.setRating(entity.getRating());
        dto.setUsername(user_name);
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
