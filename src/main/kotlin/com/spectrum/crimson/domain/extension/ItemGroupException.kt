package com.spectrum.crimson.domain.extension

import com.spectrum.crimson.common.utils.TimeUtil
import com.spectrum.crimson.domain.entity.ItemGroup

fun ItemGroup.toProto(): com.spectrum.crimson.proto.ItemGroup {
    val builder = com.spectrum.crimson.proto.ItemGroup.newBuilder()

    builder.setId(this.id)
    builder.setStatus(com.spectrum.crimson.proto.ItemGroup.ItemGroupStatus.valueOf(this.status.name))
    builder.setDescription(this.description)
    builder.setCreatedAt(TimeUtil.toEpochMilli(this.createdAt))
    builder.setUpdatedAt(TimeUtil.toEpochMilli(this.updatedAt))

    return builder.build()
}