package com.aiclass03team07.bookapp.service;

import com.aiclass03team07.bookapp.exception.BookNotFoundException;
import com.aiclass03team07.bookapp.entity.BookDetail;
import com.aiclass03team07.bookapp.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class DetailService {
    private final DetailRepository detailRepository;

    @Transactional(readOnly = true)
    public BookDetail findById(Long id){
        return detailRepository.findById(id).orElseThrow(()
        -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public void deleteBook(Long id){
        if(detailRepository.existsById(id)){
            detailRepository.deleteById(id);
        } else{
            throw new BookNotFoundException(id);
        }
    }

    @Transactional
    public void updateLikes(Long id){
        BookDetail book = detailRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setLikesCount(book.getLikesCount() + 1);
    }

    @Transactional
    public long count(){
        return detailRepository.count();
    }

    @Transactional(readOnly = true)
    public Long getLikesCount(Long id){
        BookDetail book = detailRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return book.getLikesCount();
    }






}
