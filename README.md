# 📚 도서관리시스템

> KT AIVLE School 9기 미니프로젝트 5차  
> **3반 7조** | AI를 활용한 도서표지 이미지 생성 도서관리시스템- Backend(Spring Boot)

---

## 1. 프로젝트 개요

AI(OpenAI GPT Image API)를 활용하여 도서 표지 이미지를 자동 생성할 수 있는 도서관리 웹 서비스입니다.  
사용자는 조회,리뷰,별점,찜하기 할 수 있습니다.
관리자는 도서를 등록·조회·삭제하고, AI가 생성한 이미지를 표지로 저장할 수 있습니다.
기존 4차 미니프로젝트의 Mock Server(`json-server`) 환경을 **Spring Boot**로 전환하여 안정적인 데이터 관리와 API 서비스를 제공하는 백엔드 서버입니다. 
JPA를 이용한 데이터베이스 연동 및 외부 OpenAI API 연동을 통해 고도화된 도서 관리 기능을 구현합니다

### 주요 기능

| 분류 | 기능 | 설명 |
|---|---|---|
| **인증** | 관리자/게스트 로그인 | 사용자 유형별 권한 분리 및 페이지 접근 제어 |
| **도서 관리** | 도서 목록/상세 조회 | 카드 형식 목록 및 상세 정보(표지, 서지, 내용) 조회 |
| | 도서 검색 및 정렬 | 제목·저자 실시간 검색 및 최신순·제목순 정렬 |
| | 도서 등록/삭제 | 필수 유효성 검사를 포함한 도서 정보 등록 및 삭제 |
| **관리자 전용** | 관리자 페이지 | 도서 관리 및 메인 배너(신간/추천) 광고 AI 생성 인터페이스 |
| **AI 기능** | 표지 생성 | 도서 정보 기반 프롬프트 생성 및 GPT Image API로 표지 저장 |
| **사용자 활동** | 좋아요 및 찜하기 | 도서별 좋아요 수 증가(PATCH) 및 게스트용 도서 찜 기능 |
| | 별점 및 리뷰 | 상세 페이지 내 평점 등록 및 리뷰 작성/조회 |
| **기타** | URL 공유 | 도서 상세 페이지 URL 클립보드 복사 기능 |

---

## 2. 기술 스택

### Frontend
| 기술 | 버전 |
|---|---|
| React | ^19.2.6 |
| Vite | ^8.0.12 |
| react-router-dom | ^7.15.1 |

### Backend
| 기술 | 설명 |
|---|---|
| Java | 17+ |
| Spring Boot | 3.x |
| Spring Data JPA | 데이터 영속성 계층 |
| H2 Database | 인메모리 데이터베이스 |
| Lombok & Validation | 코드 간결화 및 유효성 검사 |


### Mock Server
| 기술 | 버전 |
|---|---|
| json-server | ^0.17.4 |

---

### AI API
| 기술 | 설명 |
|---|---|
| OpenAI GPT Image API | 도서 표지 이미지 생성 |
| OpenAI GPT Image API | 도서 표지 및 배너 광고 이미지 자동 생성 |

---
```
## 3. 프로젝트 구조

---
bookapp/
├── README.md
└── Backend/                                      # 실제 백엔드 애플리케이션
    ├── build.gradle                             # Gradle 빌드 설정 및 의존성 관리
    ├── settings.gradle                          # Gradle 프로젝트 이름 설정
    ├── gradlew                                  # Linux/macOS용 Gradle Wrapper
    ├── gradlew.bat                              # Windows용 Gradle Wrapper
    ├── gradle/
    │   └── wrapper/
    │       ├── gradle-wrapper.jar               # Gradle Wrapper 실행 파일
    │       └── gradle-wrapper.properties        # Gradle Wrapper 버전 및 배포 설정
    └── src/
        └── main/
            ├── resources/
            │   └── application.yaml             # 서버 포트, DB, JPA, Swagger, API 키 설정
            └── java/
                └── com/
                    └── aiclass03team07/
                        └── bookapp/
                            ├── BookappApplication.java
                            │                                      # Spring Boot 애플리케이션 시작점
                            ├── config/                            # 공통 설정 클래스
                            │   ├── H2ServerConfig.java            # H2 DB 서버/콘솔 관련 설정
                            │   ├── PasswordConfig.java            # 비밀번호 암호화 관련 Bean 설정
                            │   ├── RestTemplateConfig.java        # 외부 API 호출용 RestTemplate 설정
                            │   ├── SwaggerConfig.java             # Swagger/OpenAPI 문서 설정
                            │   ├── WebMvcConfig.java              # CORS 등 Web MVC 설정
                            │   └── WebConfig.java                 # 추가 Web MVC 공통 설정
                            ├── controller/                        # 클라이언트 요청을 받는 API 계층
                            │   ├── bookcreate/
                            │   │   └── BookCreateController.java  # 도서 등록, 배너 업로드 API
                            │   ├── bookdetail/
                            │   │   └── BookDetailController.java  # 도서 상세 조회, 찜 토글, 삭제 API
                            │   ├── generateimage/
                            │   │   └── GenerateImageController.java
                            │   │                                  # AI 표지 이미지 생성/저장/수정 API
                            │   ├── mainpage/
                            │   │   ├── MainBannerController.java  # 메인 배너 이미지 조회 API
                            │   │   └── MainBookController.java    # 도서 목록, 랭킹, 장르 조회 API
                            │   ├── review/
                            │   │   └── ReviewController.java      # 리뷰 조회 및 저장 API
                            │   └── user/
                            │       └── UserController.java        # 회원가입, 로그인, 로그아웃, 사용자 정보 API
                            ├── dto/                               # 요청/응답 데이터 전달 객체
                            │   ├── bookcreate/
                            │   │   └── BookCreateDTO.java         # 도서 등록 요청 DTO
                            │   ├── bookdetail/
                            │   │   ├── DetailDTO.java             # 도서 상세 응답 DTO
                            │   │   └── SaveDTO.java               # 도서 상세 저장 관련 DTO
                            │   ├── generateimage/
                            │   │   ├── GenerateImageCreateRequestDto.java
                            │   │   │                              # AI 이미지 생성 요청 DTO
                            │   │   ├── GenerateImageRequestDto.java
                            │   │   │                              # 표지 이미지 저장/수정 요청 DTO
                            │   │   └── GenerateImageResponseDto.java
                            │   │                                  # 표지 이미지 응답 DTO
                            │   ├── mainpage/
                            │   │   ├── BookListDTO.java           # 도서 목록 응답 DTO
                            │   │   ├── CreateBannerDTO.java       # 배너 생성 요청 DTO
                            │   │   └── MainBannerDTO.java         # 메인 배너 URL 응답/저장 DTO
                            │   ├── review/
                            │   │   ├── GetReviewDTO.java          # 리뷰 조회 응답 DTO
                            │   │   └── SaveReviewDTO.java         # 리뷰 저장 요청 DTO
                            │   └── user/
                            │       ├── UserJoinRequestDto.java    # 회원가입 요청 DTO
                            │       └── UserLoginRequestDto.java   # 로그인 요청 DTO
                            ├── entity/                            # DB 테이블과 매핑되는 JPA 엔티티
                            │   ├── BannerImageUrlEntity.java      # 메인 배너 이미지 URL 엔티티
                            │   ├── BookEntity.java                # 도서 정보 엔티티
                            │   ├── GenerateImageEntity.java       # AI/표지 이미지 정보 엔티티
                            │   ├── PreferenceGenreEntity.java     # 사용자 선호 장르 엔티티
                            │   ├── ReviewEntity.java              # 리뷰 엔티티
                            │   ├── UserEntity.java                # 사용자 엔티티
                            │   └── WishListEntity.java            # 찜 목록 엔티티
                            ├── exception/                         # 예외 처리
                            │   ├── BookNotFoundException.java     # 도서 미존재 예외
                            │   ├── DuplicateUserIdException.java  # 회원가입 시 아이디 중복 예외    
                            │   ├── LoginFailedException.java      # 로그인 실패 예외
                            │   ├── LoginRequiredException.java    # 로그인이 필요한 요청에 대한 예외
                            │   ├── UserNotFoundException.java     # 사용자 미존재 예외
                            │   └── GlobalExceptionHandler.java    # 전역 예외 처리 핸들러
                            ├── repository/                        # DB 접근 계층
                            │   ├── BannerImageUrlRepository.java  # 배너 이미지 Repository
                            │   ├── BookRepository.java            # 도서 Repository
                            │   ├── GenerateImageRepository.java   # 표지 이미지 Repository
                            │   ├── PreferenceRepository.java      # 선호 장르 Repository
                            │   ├── ReviewRepository.java          # 리뷰 Repository
                            │   ├── UserRepository.java            # 사용자 Repository
                            │   └── WishlistRepository.java        # 찜 Repository
                            └── service/                           # 비즈니스 로직 계층
                                ├── bookcreate/
                                │   └── BookCreateService.java     # 도서 등록 처리
                                ├── bookdetail/
                                │   └── BookDetailService.java     # 도서 상세, 찜, 삭제 처리
                                ├── generateimage/
                                │   └── GenerateImageService.java  # OpenAI 이미지 생성 및 표지 저장 처리
                                ├── mainpage/
                                │   ├── MainBannerService.java     # 메인 배너 조회/저장 처리
                                │   └── MainPageService.java       # 도서 목록, 랭킹, 장르 조회 처리
                                ├── review/
                                │   └── ReviewService.java         # 리뷰 조회/저장 처리
                                └── user/
                                    └── UserService.java           # 회원가입, 로그인, 중복 확인 처리
```

---

## 4. 실행 방법



### 환경 변수 설정

프로젝트 루트에 `.env` 파일을 생성하고 OpenAI API Key를 입력합니다.

```env
API_KEY=your_openai_api_key
```

> **주의:** `.env` 파일은 절대 Git에 커밋하지 마세요.

### H2 데이터베이스 설정
```
src/main/resources/application.yaml 기준 기본 설정은 다음과 같습니다.
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:bookdb;DB_CLOSE_DELAY=-1
    username: sa
    password: 1234
  h2:
    console:
      enabled: true
      path: /h2-console
```

### 서버 실행

| 서버 | 주소 |
|---|---|
| React 개발 서버 | http://localhost:5173 |

| 서버 | 주소 |
|---|---|
| Spring Boot API 서버 | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger |
| H2 Console | http://localhost:8080/h2-console |

---

## 5. API 명세

| 구분 | 서비스명 | API 이름 | Method | REST API |
|---|---|---|---|---|
| 사용자 | UserController | 아이디 중복 확인 | GET | `/api/users/check-id?userId={userId}` |
| 사용자 | UserController | 회원가입 | POST | `/api/users/join` |
| 사용자 | UserController | 로그인 | POST | `/api/users/login` |
| 사용자 | UserController | 로그아웃 | POST | `/api/users/logout` |
| 사용자 | UserController | 현재 로그인 사용자 정보 조회 | GET | `/api/users/me` |
| 메인페이지 | MainBookController | 도서 목록 조회 | GET | `/api/books/getall` |
| 메인페이지 | MainBookController | 좋아요 랭킹 조회 | GET | `/api/books/ranking` |
| 메인페이지 | MainBookController | 장르 목록 조회 | GET | `/api/books/genres` |
| 메인페이지 | MainBannerController | 메인 배너 이미지 조회 | GET | `/api/banner/get` |
| 도서 등록 페이지 | BookCreateController | 새로운 도서 등록 | POST | `/api/bookcreate/create` |
| 도서 등록 페이지 | BookCreateController | 배너 이미지 URL 저장 | POST | `/api/bookcreate/banner-upload` |
| 상세 정보 페이지 | BookDetailController | 메인 카드용 도서 목록 조회 | GET | `/api/bookdetail/books` |
| 상세 정보 페이지 | BookDetailController | id별 도서 상세 조회 | GET | `/api/bookdetail/book/{id}` |
| 상세 정보 페이지 | BookDetailController | 도서 찜 토글 | POST | `/api/bookdetail/book/{id}?userId={userId}` |
| 상세 정보 페이지 | BookDetailController | id별 도서 삭제 | DELETE | `/api/bookdetail/book/{id}` |
| 리뷰 | ReviewController | id별 도서 리뷰 전체 조회 | GET | `/api/review/{id}/getallreview` |
| 리뷰 | ReviewController | id별 도서 리뷰 저장 | POST | `/api/review/{id}/save` |
| AI 이미지 생성 페이지 | GenerateImageController | 도서 정보 및 표지 이미지 조회 | GET | `/api/books/{bookId}` |
| AI 이미지 생성 페이지 | GenerateImageController | AI 표지 이미지 생성 및 저장 | POST | `/api/books/{bookId}/generate` |
| AI 이미지 생성 페이지 | GenerateImageController | 표지 이미지 수동 저장 및 수정 | POST | `/api/books/{bookId}/image` |
| AI 이미지 생성 페이지 | GenerateImageController | 표지 이미지 정보 수정 | PATCH | `/api/books/{bookId}` |
---

## 6. 주요 구현사항

### 메인 화면 

### 사용자 기능

- 아이디 중복 확인 API 구현
- 회원가입 API 구현
- 비밀번호 암호화 처리
- 로그인 API 구현
- 로그인 성공 시 세션에 사용자 정보 및 권한 저장
- 로그아웃 시 세션 만료 처리
- 현재 로그인 사용자 정보 조회 API 구현
- 사용자별 선호 장르 저장 구조 구현

### 메인페이지 기능

- 전체 도서 목록 조회 API 구현
- 키워드 기반 도서 검색 기능 구현
- 페이징 처리 지원
- 정렬 기준 및 정렬 방향 선택 지원
- 좋아요 기준 도서 랭킹 조회 API 구현
- 장르별 랭킹 조회 지원
- 등록된 도서의 전체 장르 목록 조회 API 구현

### 도서 등록 기능

- 제목, 저자, 출판사, 발행년도, 총서사항, 장르, 도서 내용 저장
- 필수 입력값 유효성 검사 적용
- 제목, 저자, 내용 등 글자 수 제한 적용
- 도서 정보 DB 저장 로직 구현
- 배너 이미지 URL 저장 API 구현

### 도서 상세 기능

- id별 도서 상세 정보 조회 API 구현
- 사용자 id 기준 찜 여부 확인 기능 구현
- 도서 찜 등록 및 취소 토글 기능 구현
- 도서 삭제 API 구현
- 메인 카드용 도서 목록 조회 API 구현
- 도서가 존재하지 않을 경우 예외 처리 구현

### 리뷰 기능

- 특정 도서의 리뷰 전체 조회 API 구현
- 특정 도서에 리뷰 저장 API 구현
- 리뷰 내용, 평점, 사용자 정보 저장
- 도서와 리뷰 간 연관관계 기반 조회 처리

### AI 표지 이미지 생성 기능

- 도서 정보 기반 AI 이미지 생성 프롬프트 자동 구성
- 사용자 추가 프롬프트 입력 지원
- 이미지 모델, 해상도, 품질 옵션 선택 지원
- OpenAI Images API 연동
- 생성된 이미지를 Base64 Data URL 형태로 저장
- 도서별 표지 이미지 조회 기능 구현
- 표지 이미지 수동 저장 및 수정 API 구현
- 기존 표지 이미지가 있을 경우 업데이트 처리

### 메인 배너 기능

- 최신 도서 배너 이미지 URL 저장 및 조회
- 인기 도서 배너 이미지 URL 저장 및 조회
- AI 추천 도서 배너 이미지 URL 저장 및 조회
- 관리자 또는 등록 페이지에서 배너 이미지 URL 저장 가능
- 저장된 배너 URL을 메인페이지에서 사용할 수 있도록 DTO로 응답
- OpenAI Images API 연동

---


## 7. H2 데이터베이스 구조

> 본 프로젝트는 `db.json`이 아니라 H2 인메모리 데이터베이스를 사용합니다.  
> 애플리케이션 실행 시 JPA 설정에 따라 테이블이 자동 생성됩니다.

### BookEntity

도서 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 도서 식별자, 자동 증가 |
| `title` | String | 도서 제목 |
| `author` | String | 저자 |
| `isbn` | String | ISBN |
| `content` | String | 도서 내용 |
| `genre` | String | 장르 |
| `publisher` | String | 출판사 |
| `seriesInfo` | String | 총서사항 |
| `publishedDt` | String | 발행년도 |
| `createdAt` | LocalDateTime | 등록일, 자동 생성 |
| `updatedAt` | LocalDateTime | 수정일 |
| `generateImageEntity` | GenerateImageEntity | 도서 표지 이미지 정보와 1:1 연결 |
| `reviewEntities` | List<ReviewEntity> | 도서 리뷰 목록 |

### GenerateImageEntity

AI 표지 이미지 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 이미지 식별자, 자동 증가 |
| `coverImageUrl` | String | 표지 이미지 경로 또는 Base64 Data URL |
| `imageModel` | String | AI 이미지 생성 모델명 |
| `resolution` | String | 이미지 해상도 |
| `quality` | String | 이미지 품질 |
| `coverPrompt` | String | 이미지 생성에 사용된 프롬프트 |
| `bookEntity` | BookEntity | 연결된 도서 정보 |

### UserEntity

사용자 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 사용자 식별자, 자동 증가 |
| `userId` | String | 로그인 아이디, 중복 불가 |
| `password` | String | 암호화된 비밀번호 |
| `role` | String | 사용자 권한 |
| `reveiwlist` | List<ReviewEntity> | 사용자가 작성한 리뷰 목록 |

### ReviewEntity

도서 리뷰 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 리뷰 식별자, 자동 증가 |
| `content` | String | 리뷰 내용 |
| `rating` | Long | 평점 |
| `createdAt` | LocalDateTime | 리뷰 작성일, 자동 생성 |
| `bookEntity` | BookEntity | 리뷰가 작성된 도서 |
| `userentity` | UserEntity | 리뷰 작성자 |

### WishListEntity

사용자의 도서 찜 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 찜 식별자, 자동 증가 |
| `userId` | Long | 사용자 식별자 |
| `bookId` | Long | 도서 식별자 |

> `userId`와 `bookId` 조합은 중복 저장되지 않도록 unique 제약조건이 설정되어 있습니다.

### PreferenceGenreEntity

사용자 선호 장르 정보를 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 선호 장르 식별자, 자동 증가 |
| `user` | UserEntity | 연결된 사용자 |
| `genre` | String | 선호 장르 |

### BannerImageUrlEntity

메인 배너 이미지 URL을 저장하는 테이블입니다.

| 필드명 | 타입 | 설명 |
|---|---|---|
| `id` | Long | 배너 식별자, 자동 증가 |
| `latestBanner` | String | 최신 도서 배너 이미지 URL |
| `bestBanner` | String | 인기 도서 배너 이미지 URL |
| `aiRecommendBanner` | String | AI 추천 도서 배너 이미지 URL |
---
## 8. Troubleshooting

프로젝트 진행 중 발생한 주요 문제와 해결 방안입니다.

| 구분 | 현상 | 원인 | 해결 |
| :--- | :--- | :--- | :--- |
| **빌드** | Springdoc 버전 업그레이드 미적용 | Gradle 캐시에 구버전 라이브러리 잔존 | PowerShell로 캐시 삭제 후 재빌드 |
| **인증/세션** | 로그아웃 후에도 관리자 버튼 노출 | 세션 쿠키(JSESSIONID) 캐싱 간섭 | 엄격한 조건문 적용 및 `setMaxAge(0)`으로 쿠키 강제 폐기 |
| **DB 저장** | `DataTruncation` 에러 발생 | Base64 데이터가 VARCHAR 크기 초과 | `@Lob` 또는 `@Column(columnDefinition = "LONGTEXT")` 사용 |
---
## 팀원

| 이름 | 담당 | 담당 |
|---|---|---|
| 김도원 | 메인 화면 |로그인 기능|
| 김준형 | 메인 화면 |리뷰,별점,찜하기,메인 배너|
| 이영아 | 도서 등록 페이지 |리뷰,별점,찜하기|
| 이민주 | 도서 상세 페이지 |리뷰,별점,찜하기|
| 김한수 | 도서 상세 페이지 | 메인 배너 |
| 김수정 | AI 표지 생성 페이지 |로그인 기능|
| 주상현 | AI 표지 생성 페이지 |로그인 기능|
