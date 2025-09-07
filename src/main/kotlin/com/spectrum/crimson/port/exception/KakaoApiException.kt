package com.spectrum.crimson.port.exception

/**
 * 카카오 API 호출 중 발생하는 예외
 */
class KakaoApiException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    
    constructor(
        message: String,
        httpStatus: Int? = null,
        cause: Throwable? = null
    ) : this(
        message = if (httpStatus != null) {
            "$message (HTTP Status: $httpStatus)"
        } else {
            message
        },
        cause = cause
    )
}
