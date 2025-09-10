package com.spectrum.crimson.domain.extension

import com.spectrum.crimson.common.utils.TimeUtil
import com.spectrum.crimson.domain.entity.Place

fun Place.toProto(): com.spectrum.crimson.proto.Place {
    val builder = com.spectrum.crimson.proto.Place.newBuilder()

    builder.setId(this.id)
    builder.setLocationId(this.locationId)
    builder.setName(this.name)
    builder.setCategoryName(this.categoryName)
    builder.setAddressName(this.addressName)
    builder.setPhone(this.phone)
    builder.setUrl(this.url)
    builder.setLat(this.lat)
    builder.setLng(this.lng)

    builder.setMember(this.member.toProto())

    builder.setCreatedAt(TimeUtil.toEpochMilli(this.createdAt))
    builder.setUpdatedAt(TimeUtil.toEpochMilli(this.updatedAt))

    return builder.build()
}