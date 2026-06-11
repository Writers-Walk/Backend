package com.aiclass03team07.bookapp.service.mainpage;


import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainBannerService {
    private  final BookRepository bookRepository;

    public BookEntity getLatestBook() {
        // 0페이지에서 1개만 가져오되, createdAt 기준으로 내림차순 정렬
        Page<BookEntity> page = bookRepository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return page.hasContent() ? page.getContent().get(0) : null;
    }

    //관리자 계정일때 배너 생성하는


    public BookEntity getMostLikedBook() {
        return bookRepository.findTopByOrderByLikesDesc();
    }


}
