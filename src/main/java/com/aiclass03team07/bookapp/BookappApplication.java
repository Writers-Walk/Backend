package com.aiclass03team07.bookapp;

import com.aiclass03team07.bookapp.entity.BookEntity;
import com.aiclass03team07.bookapp.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookappApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(DetailRepository detailRepository) {
//		return args -> {
//			BookDetail b1 = new BookDetail();
//			b1.setTitle("자바의 정석");
//			b1.setAuthor("남궁성");
//			detailRepository.save(b1);
//
//			BookDetail b2 = new BookDetail();
//			b2.setTitle("Spring 입문");
//			b2.setAuthor("임한울");
//			detailRepository.save(b2);
//
//			BookDetail b3 = new BookDetail();
//			b3.setTitle("React 시작");
//			b3.setAuthor("홍길동");
//			detailRepository.save(b3);
//
//			BookDetail b4 = new BookDetail();
//			b4.setTitle("자바의 기초");
//			b4.setAuthor("임한울");
//			detailRepository.save(b4);
//
//			BookDetail b5 = new BookDetail();
//			b5.setTitle("node.js 실전");
//			b5.setAuthor("김에이블");
//			detailRepository.save(b5);
//		};
//	}

	//GenerateImage Test용
	@Bean
	CommandLineRunner initImageTest(BookRepository bookRepository) {
		return args -> {
			BookEntity testBook1 = new BookEntity();
			testBook1.setTitle("AI 이미지 테스트용 책 1");
			testBook1.setAuthor("작자미상");
			testBook1.setContent("신비로운 마법 도서관과 관련된 판타지 소설입니다.");
			testBook1.setGenre("판타지");
			testBook1.setPublisher("test1");
			testBook1.setIsbn("test1");
			bookRepository.save(testBook1);

			BookEntity testBook2 = new BookEntity();
			testBook2.setTitle("AI 이미지 테스트용 책 2");
			testBook2.setAuthor("김에이블");
			testBook2.setContent("네온 사인이 가득한 미래 도시를 배경으로 한 사이버펑크 액션입니다.");
			testBook2.setGenre("SF");
			testBook2.setIsbn("test2");
			testBook2.setPublisher("test2");
			bookRepository.save(testBook2);

			System.out.println("🚀 [GenerateImage 테스트 전용] 가짜 책 데이터 2개가 DB에 주입되었습니다!");
		};
	}
	//GenerateImage Test용

}