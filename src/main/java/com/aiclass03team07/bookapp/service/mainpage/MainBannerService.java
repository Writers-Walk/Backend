package com.aiclass03team07.bookapp.service.mainpage;


import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainBannerService {
    private  final BookRepository bookRepository;

    //book 정보 넣고 저장
    public void saveBookInfo(BookCreateDTO dto){
        BookEntity entity = convertToBookEntity(dto);
        bookRepository.save(entity);
    }



    //관리자 계정일때 배너 생성하는


    //dto -> entity
    private BookEntity convertToBookEntity (BookCreateDTO dto){
        BookEntity entity = new BookEntity();
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setLikes(0L);
        entity.setContent(dto.getContent());
        entity.setGenre(dto.getGenre());
        entity.setPublisher(dto.getPublisher());
        entity.setSeriesInfo(dto.getSeriesInfo());
        entity.setPublishedDt(dto.getPublishedDt());
        entity.setUpdatedAt(null);

        return entity;
    }



}
