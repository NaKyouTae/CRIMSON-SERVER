-- PostgreSQL 초기화 스크립트
-- 데이터베이스 생성 (PostgreSQL 문법)
-- PostgreSQL에서는 docker-compose.yml에서 이미 데이터베이스가 생성되므로 여기서는 생략
-- CREATE DATABASE crimson; -- 이미 docker-compose.yml에서 POSTGRES_DB로 생성됨

-- 사용자 권한 설정
GRANT ALL PRIVILEGES ON DATABASE crimson TO postgres;

-- 확장 기능 활성화 (필요시)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 연결 확인
SELECT version();
