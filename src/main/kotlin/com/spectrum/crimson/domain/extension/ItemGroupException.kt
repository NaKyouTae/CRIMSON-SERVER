package com.spectrum.crimson.domain.extension

import com.spectrum.crimson.common.utils.TimeUtil
import com.spectrum.crimson.domain.entity.PlaceGroup

fun PlaceGroup.toProto(): com.spectrum.crimson.proto.PlaceGroup {
    val builder = com.spectrum.crimson.proto.PlaceGroup.newBuilder()

    builder.setId(this.id)
    builder.setStatus(com.spectrum.crimson.proto.PlaceGroup.PlaceGroupStatus.valueOf(this.status.name))
    builder.setDescription(this.memo)
    builder.setCreatedAt(TimeUtil.toEpochMilli(this.createdAt))
    builder.setUpdatedAt(TimeUtil.toEpochMilli(this.updatedAt))

    return builder.build()
}