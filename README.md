# Crimson Server

Spring Boot Kotlin 기반의 서버 애플리케이션입니다.

## 기술 스택

- **Backend**: Spring Boot 3.5.6, Kotlin 1.9.25
- **Database**: PostgreSQL 16
- **ORM**: Spring Data JPA + Hibernate
- **Migration**: Liquibase
- **Cache**: Redis
- **Monitoring**: Prometheus + Grafana

## 데이터베이스 설정

### PostgreSQL 연결

애플리케이션은 PostgreSQL을 사용합니다. 다음과 같은 방법으로 연결할 수 있습니다:

#### 1. 로컬 PostgreSQL (Docker)

```bash
# PostgreSQL 컨테이너 시작
docker-compose -f docker-compose.yml up -d postgres

# 연결 테스트
./test-postgres-connection.sh
```

#### 2. Supabase (클라우드)

`.env` 파일에서 Supabase 연결 정보를 설정:

```bash
DB_HOST=your-supabase-host
DB_NAME=postgres
DB_USER_NAME=postgres
DB_PASSWORD=your-password
```

## 애플리케이션 실행

### 1. 로컬 실행

```bash
# 환경 변수 설정
export SHOW_LOG=true
export DB_HOST=localhost
export DB_NAME=crimson_db
export DB_USER_NAME=postgres
export DB_PASSWORD=postgres
export LIQUIBASE_RUN=true

# 애플리케이션 실행
./gradlew bootRun
```

### 2. Docker 실행

```bash
# 전체 서비스 실행
docker-compose -f docker-compose.yml up -d

# 애플리케이션만 빌드 및 실행
docker build -t crimson-server:local .
docker-compose -f docker-compose.yml up crimson-service
```

## Liquibase 마이그레이션

데이터베이스 스키마 변경사항은 `src/main/resources/db/changelog/` 디렉토리에 있습니다.

### 마이그레이션 실행

```bash
# Liquibase 실행
export LIQUIBASE_RUN=true
./gradlew bootRun
```

### 새로운 마이그레이션 추가

1. `src/main/resources/db/changelog/` 디렉토리에 새 파일 생성
2. `db.changelog-master.yml`에 새 파일 포함

## 문제 해결

### PostgreSQL 연결 실패

1. **데이터베이스 서비스 상태 확인**
   ```bash
   docker ps | grep postgres
   ```

2. **연결 테스트**
   ```bash
   ./test-postgres-connection.sh
   ```

3. **로그 확인**
   ```bash
   docker logs postgres
   ```

### Liquibase 실행 실패

1. **환경 변수 확인**
   ```bash
   echo $LIQUIBASE_RUN
   echo $DB_HOST
   echo $DB_NAME
   ```

2. **데이터베이스 권한 확인**
   ```bash
   docker exec -it postgres psql -U postgres -d crimson_db
   ```

## 개발 환경

- **Java**: 21
- **Kotlin**: 1.9.25
- **Spring Boot**: 3.5.6-SNAPSHOT
- **PostgreSQL**: 16
- **Redis**: 8.2.0
