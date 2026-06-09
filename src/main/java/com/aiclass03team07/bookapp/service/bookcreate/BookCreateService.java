package com.aiclass03team07.bookapp.service.bookcreate;

import com.aiclass03team07.bookapp.dto.bookcreate.BookCreateDTO;
import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCreateService {
    private final BookRepository bookRepository;

    public void saveBookInfo(BookCreateDTO dto){

    }

    //dto -> entity
    private BookEntity convertToBookEntity (BookCreateDTO dto){
        BookEntity entity = new BookEntity();
        entity.setAuthor(dto.getAuthor());
        entity.setGenre(dto.getGenre());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setPublisher(dto.getPublisher());
        entity.setpubli
    }
}
