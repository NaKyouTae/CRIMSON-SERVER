#!/bin/bash

echo "PostgreSQL 연결 테스트 시작..."

# 환경 변수 로드
source .env

# PostgreSQL 연결 테스트
echo "호스트: $DB_HOST"
echo "데이터베이스: $DB_NAME"
echo "사용자: $DB_USER_NAME"

# psql이 설치되어 있는지 확인
if command -v psql &> /dev/null; then
    echo "psql 명령어를 사용하여 연결 테스트..."
    PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -U "$DB_USER_NAME" -d "$DB_NAME" -c "SELECT version();"
else
    echo "psql이 설치되어 있지 않습니다. Docker 컨테이너를 통해 테스트합니다..."
    
    # Docker 컨테이너가 실행 중인지 확인
    if docker ps | grep -q postgres; then
        echo "PostgreSQL 컨테이너가 실행 중입니다."
        docker exec postgres psql -U postgres -d postgres -c "SELECT version();"
    else
        echo "PostgreSQL 컨테이너가 실행되지 않았습니다."
        echo "다음 명령어로 컨테이너를 시작하세요:"
        echo "docker-compose -f docker-compose-postgres.yml up -d postgres"
    fi
fi

echo "연결 테스트 완료."
