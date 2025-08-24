-- PostgreSQL 초기화 스크립트
-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS crimson;

-- 사용자 권한 설정
GRANT ALL PRIVILEGES ON DATABASE crimson TO postgres;

-- 확장 기능 활성화 (필요시)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 연결 확인
SELECT version();
