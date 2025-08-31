package com.spectrum.crimson.domain.model

import com.spectrum.crimson.proto.Token

data class SpectrumToken(
    val accessToken: String,
    val refreshToken: String,
) {

    fun toProto(): Token = Token.newBuilder()
        .setAccessToken(this.accessToken)
        .setRefreshToken(this.refreshToken)
        .build()
}