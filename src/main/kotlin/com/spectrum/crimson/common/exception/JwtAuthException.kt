package com.spectrum.crimson.common.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthException(
    message: String,
): AuthenticationException(message) { }