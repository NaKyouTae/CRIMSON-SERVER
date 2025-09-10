package com.spectrum.crimson.domain.extension

import com.spectrum.crimson.common.utils.TimeUtil
import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.entity.Place

fun Member.toProto(): com.spectrum.crimson.proto.Member {
    val builder = com.spectrum.crimson.proto.Member.newBuilder()

    builder.setId(this.id)
    builder.setName(this.name)
    builder.setPhone(this.phone)
    builder.setEmail(this.email)

    builder.setCreatedAt(TimeUtil.toEpochMilli(this.createdAt))
    builder.setUpdatedAt(TimeUtil.toEpochMilli(this.updatedAt))

    return builder.build()
}