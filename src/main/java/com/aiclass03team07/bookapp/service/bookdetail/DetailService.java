//package com.aiclass03team07.bookapp.service.bookdetail;
//
//import com.aiclass03team07.bookapp.dto.bookdetail.DetailDTO;
//import com.aiclass03team07.bookapp.entity.BookEntity;
//import com.aiclass03team07.bookapp.repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//
//public class DetailService {
//    private final BookRepository bookRepository;
//
////    @Transactional(readOnly = true)
////    public BookDetail findById(Long id){
////        return detailRepository.findById(id).orElseThrow(()
////        -> new BookNotFoundException(id));
////    }
////
////    @Transactional(readOnly = true)
////    public void deleteBook(Long id){
////        if(detailRepository.existsById(id)){
////            detailRepository.deleteById(id);
////        } else{
////            throw new BookNotFoundException(id);
////        }
////    }
////
////    @Transactional
////    public void updateLikes(Long id){
////        BookDetail book = detailRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
////
////        book.setLikesCount(book.getLikesCount() + 1);
////    }
////
////    @Transactional
////    public long count(){
////        return detailRepository.count();
////    }
////
////    @Transactional(readOnly = true)
////    public Long getLikesCount(Long id){
////        BookDetail book = detailRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
////        return book.getLikesCount();
////    }
//    // dddd
//
//
//    public DetailDTO getBookDetail(Long id){
//        BookEntity entity = bookRepository.findById(id).get();
//        DetailDTO dto = new DetailDTO();
//        dto.setLikes(entity.getLikes());
//        dto.setContent(entity.getContent());
//        dto.setGenre(entity.getGenre());
//        dto.setAuthor(entity.getAuthor());
//        dto.setPublisher(entity.getPublisher());
//        dto.setTitle(entity.getTitle());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setSeriesInfo(entity.getSeriesInfo());
//        dto.setUpdatedAt(entity.getUpdatedAt());
//        //dto.setCoverImageUrl(entity.getCoverImageUrl);
//        dto.setPublicatiedDt(entity.getPublishedDt());
//        return dto;
//    }
//
//
//
//
//
//}
