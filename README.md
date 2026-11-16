# Cloud Chap Task

## 1. 프로젝트 소개

AWS 클라우드 환경에서 Spring Boot 기반의 팀원 정보 등록 및 조회 API를 구현하고 배포하는 프로젝트입니다.

## 2. 기술 스택

- **Language** : Java 17
- **Framework** : Spring Boot, Spring Web MVC
- **Database** : H2, MySQL
- **ORM** : Spring Data JPA
- **Library** : Lombok, Validation
- **Logging** : SLF4J
- **Monitoring** : Spring Boot Actuator
- **Build Tool** : Gradle
- **Infrastructure** : AWS VPC, EC2
- **Version Control** : Git, GitHub
- **API Test** : Postman

---

## 3. Lv 0 - AWS 비용 관리

- 월 예산: $100
- 예산 80% 도달 시 이메일 알림 설정

### AWS Budgets 설정
<img width="1768" height="756" alt="image" src="https://github.com/user-attachments/assets/bce623b7-2d7c-4b8b-8002-57ad34408865" />

---

## 4. Lv 1 - 인프라 구축 및 애플리케이션 배포

### 4-1. AWS 인프라 구성

- VPC 생성
- Public / Private Subnet 분리
- Public Subnet에 EC2 배치

### 4-2. API 기능 & 테이블 명세서

- [테이블 명세서](https://savory-sort-03c.notion.site/419d2e2ddefa8064b437e544fe1223c5?source=copy_link)
- [팀원 정보 등록](https://savory-sort-03c.notion.site/API-40dd2e2ddefa80c5acd9faa462b73599?source=copy_link)
- [팀원 정보 조회](https://savory-sort-03c.notion.site/API-419d2e2ddefa80a8b4d9cb4d9257843c?source=copy_link)

### 4-3. Profile 분리

| Profile | 데이터베이스 |
| --- | --- |
| local | H2 |
| prod | MySQL |

- 실행 환경별 YAML 설정 분리
- 운영 DB 접속 정보 외부 주입 방식 적용

### 4-4. 로그 전략

- Filter를 활용한 API 요청 INFO 로그 기록
- GlobalExceptionHandler를 활용한 ERROR 로그 및 스택트레이스 기록

### 4-5. EC2 배포 및 검증

- Gradle bootJar를 활용한 JAR 파일 생성
- SCP를 활용한 EC2 파일 전송
- nohup을 활용한 백그라운드 실행
- Postman을 활용한 API 정상 동작 검증

### 4-6. EC2 접속 정보

- Public IP: `3.34.132.101`
- Health Check: `http://3.34.132.101:8080/actuator/health`

### 4-7. Health Check 결과

```json
{
    "groups": [
        "liveness",
        "readiness"
    ],
    "status": "UP"
}
```
![img.png](images/img.png)


