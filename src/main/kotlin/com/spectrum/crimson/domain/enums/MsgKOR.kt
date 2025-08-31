package com.spectrum.crimson.domain.enums

enum class MsgKOR(val message: String) {
    NOT_FOUND_USER("존재하지 않는 유저입니다."),
    NOT_FOUND_STAFF("존재하지 않는 직원입니다."),
    NOT_FOUND_OWNER("존재하지 않는 사장님입니다."),
    NOT_FOUND_STORE("존재하지 않는 매장입니다."),
    FORBIDDEN("권한이 부족합니다."),
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일 입니다."),
    ALREADY_EXIST_PHONE("이미 존재하는 전화번호 입니다."),
    ALREADY_EXIST_BUSINESS_NUMBER("이미 존재하는 사업자번호 입니다."),
    INVALID_JWT_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_JWT_TOKEN("만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN("지원하지 않는 JWT 토큰입니다."),
    MISSING_JWT_CLAIMS_TOKEN("정보가 부족한 JWT 토큰입니다."),
    MISSING_JWT_TOKEN("존재하지 않는 JWT 토큰입니다."),
    NOT_EXISTS_REFRESH_TOKEN("Refresh 토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN_NOT_EXISTS("유효하지 않은 Refresh 토큰입니다."),
    ;
}