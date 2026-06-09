package com.aiclass03team07.bookapp;

import com.aiclass03team07.bookapp.entity.BookDetail;
import com.aiclass03team07.bookapp.repository.DetailRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookappApplication.class, args);
	}

	@Bean
	CommandLineRunner init(DetailRepository detailRepository) {
		return args -> {
			BookDetail b1 = new BookDetail();
			b1.setTitle("자바의 정석");
			b1.setAuthor("남궁성");
			detailRepository.save(b1);

			BookDetail b2 = new BookDetail();
			b2.setTitle("Spring 입문");
			b2.setAuthor("임한울");
			detailRepository.save(b2);

			BookDetail b3 = new BookDetail();
			b3.setTitle("React 시작");
			b3.setAuthor("홍길동");
			detailRepository.save(b3);

			BookDetail b4 = new BookDetail();
			b4.setTitle("자바의 기초");
			b4.setAuthor("임한울");
			detailRepository.save(b4);

			BookDetail b5 = new BookDetail();
			b5.setTitle("node.js 실전");
			b5.setAuthor("김에이블");
			detailRepository.save(b5);
		};
	}
}