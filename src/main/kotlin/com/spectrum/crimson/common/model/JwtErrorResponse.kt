package com.spectrum.crimson.common.model

data class JwtErrorResponse(
    val timestamp: String,
    val status: String,
    val code: Int,
    val error: String,
    val path: String,
)
