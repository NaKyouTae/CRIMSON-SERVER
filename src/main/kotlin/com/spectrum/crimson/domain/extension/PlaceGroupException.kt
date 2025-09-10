package com.spectrum.crimson.domain.extension

import com.spectrum.crimson.common.utils.TimeUtil
import com.spectrum.crimson.domain.entity.PlaceGroup

fun PlaceGroup.toProto(): com.spectrum.crimson.proto.PlaceGroup {
    val builder = com.spectrum.crimson.proto.PlaceGroup.newBuilder()

    builder.setId(this.id)
    builder.setIcon(this.icon)
    builder.setName(this.name)
    builder.setStatus(com.spectrum.crimson.proto.PlaceGroup.PlaceGroupStatus.valueOf(this.status.name))
    builder.setCategory(com.spectrum.crimson.proto.PlaceGroup.PlaceGroupCategory.valueOf(this.category.name))
    builder.setMemo(this.memo)
    builder.setLink(this.link)
    builder.setCreatedAt(TimeUtil.toEpochMilli(this.createdAt))
    builder.setUpdatedAt(TimeUtil.toEpochMilli(this.updatedAt))

    return builder.build()
}