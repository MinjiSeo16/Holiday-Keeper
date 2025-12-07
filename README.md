## 🌍 Holiday Keeper 
외부 API 두 개만으로 최근 5 년(2020 ~ 2025) 의 전 세계 공휴일 데이터를 저장·조회·관리하는 Mini Service
구현

###  🔷 빌드 & 실행 방법
##### ▶ Build & Run
```bash
./gradlew clean build  // 프로젝트 빌드
./gradlew bootRun   // 서버 실행
```

##### ▶ 콘솔 접속
H2 Console 접속 : `http://localhost:8080/h2-console`  
Swagger UI 접속 : `http://localhost:8080/swagger-ui/index.html`

### 🧩 주요 기능
모든 기능은 개별 브랜치에서 구현  
main 브랜치로 PR → GitHub Actions CI 수행
- [초기 데이터 적재](https://github.com/MinjiSeo16/Holiday-Keeper/pull/2)
- [필터 기반 검색](https://github.com/MinjiSeo16/Holiday-Keeper/pull/3)
- [재동기화](https://github.com/MinjiSeo16/Holiday-Keeper/pull/4)
- [삭제](https://github.com/MinjiSeo16/Holiday-Keeper/pull/5)
- [배치자동화](https://github.com/MinjiSeo16/Holiday-Keeper/pull/6)
  
### 📌 기술 스택
- Java 21, Spring Boot 3.4.12
- Spring Web / Spring Data JPA
- H2 Database
- Spring Scheduler
- Swagger (springdoc-openapi)
- GitHub Actions (CI)

### 🧪 REST API 명세 요약
#### 1. 공휴일 검색
`GET /api/holidays`  
Request Parameters: countryName, year, from, to, lastId, size (optional)
Response 예시
````json
{
  "holidays": [
    {
      "id": 12,
      "countryName": "South Korea",
      "countryCode" : "KR"
      "year": 2025,
      "name": "New Year's Day",
      "localName": "새해",
      "date": "2025-01-01"
    }
  ],
  "hasNext": true
}
````
#### 2. 특정 국가·연도 공휴일 재동기화
`POST /api/holidays/resync`  
Request Parameters : countryCode, year  
#### 3. 특정 국가·연도 공휴일 삭제
`DELETE /api/holidays`  
Request Parameters : countryCode, year  
#### 4. 자동 동기화 수동 실행
`POST /api/holidays/auto-sync`  
전년도 + 금년도 공휴일을 즉시 재동기화 (스케줄러로 자동 실행 가능)

### 📂 Database Schema

##### Country
| 필드명 | 타입 | 설명 |
|-------|------|------|
| id | BIGINT (PK) | 국가 ID |
| countryCode | VARCHAR | ISO 국가 코드 (예: KR) |
| countryName | VARCHAR | 국가 이름 |

##### Holiday
| 필드명 | 타입 | 설명 |
|-------|------|------|
| id | BIGINT (PK) | 공휴일 ID |
| country_id | BIGINT (FK) | Country 참조 |
| year | INT | 연도 |
| name | VARCHAR | 공휴일 영어명 |
| localName | VARCHAR | 공휴일 현지어 |
| date | DATE | 날짜 |

