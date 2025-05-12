# 💊Smapill

```plaintext
라즈베리파이 기반 AI 스피커 및 스마트 조명을 결합한 복약 관리 애플리케이션입니다.
라즈베리파이 기반의 AI 스피커와 스마트 조명을 활용하여 사용자의 복약 관리를 효율적으로 지원하고,
보호자와 피보호자 간의 실시간 연동 시스템을 통해 복약 상태를 공유함으로써 약물 복용 관리의 신뢰성과 편리성을 제공합니다.
```

---

## 🔧 프로젝트 설정

- **Java 17**
- **Spring Boot 3.4.0**
- **Gradle**
- **MySQL (JDBC)**
- **Spring Security & JWT 인증**
- **SpringDoc(OpenAPI 3)**
- **Redis**

---

## 📁 주요 라이브러리

| 라이브러리                          | 설명                             |
|-----------------------------------|----------------------------------|
| spring-boot-starter-web           | RESTful API 개발용 기본 설정     |
| spring-boot-starter-data-jpa      | JPA ORM 설정                     |
| spring-boot-starter-validation    | 입력 값 검증 처리                |
| spring-boot-starter-security      | 인증 및 인가 기능 구현          |
| jjwt (io.jsonwebtoken)            | JWT 토큰 생성 및 검증           |
| spring-boot-starter-data-redis    | Redis 기반 세션 및 캐시 관리    |
| springdoc-openapi-starter-webmvc-ui | Swagger UI 제공                 |
| lombok                             | 코드 간결화                      |
| mysql-connector-j                 | MySQL 드라이버                   |

---

## 🗂 프로젝트 구조

```plaintext
smapill-back/
├── .gradle/
├── .idea/
├── gradle/
├── src/
│   └── main/
│       ├── java/
│       │   └── gcu/smapill_back/
│       │       ├── apiPayload/
│       │       ├── config/
│       │       ├── converter/
│       │       ├── domain/
│       │       ├── repository/
│       │       ├── service/
│       │       └── web/
│       │           ├── controller/
│       │           └── dto/
│       │       └── SmapillBackApplication.java
│       └── resources/
│           └── application.yml

```

## 🚀 실행 방법

```bash
# 1. Git Clone
git clone https://github.com/GCU-smapill/smapill-back
cd smapill-back

# 2. Gradle Build
./gradlew clean build

# 3. 실행
./gradlew bootRun


