package com.spectrum.crimson.port.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.spectrum.crimson.proto.place.KakaoPlaceListResult
import com.spectrum.crimson.proto.KakaoPlace
import com.spectrum.crimson.proto.KakaoPlaceMeta

/**
 * 카카오 로컬 API 키워드 검색 응답 모델
 */
data class KakaoSearchResponse(
    @JsonProperty("meta")
    val meta: Meta,
    @JsonProperty("documents")
    val documents: List<Document>
)

/**
 * 응답 메타 정보
 */
data class Meta(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("is_end")
    val isEnd: Boolean,
    @JsonProperty("same_name")
    val sameName: SameName?
)

/**
 * 질의어의 지역 및 키워드 분석 정보
 */
data class SameName(
    @JsonProperty("region")
    val region: List<String>,
    @JsonProperty("keyword")
    val keyword: String,
    @JsonProperty("selected_region")
    val selectedRegion: String
)

/**
 * 장소 정보 문서
 */
data class Document(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("place_name")
    val placeName: String,
    @JsonProperty("category_name")
    val categoryName: String,
    @JsonProperty("category_group_code")
    val categoryGroupCode: String,
    @JsonProperty("category_group_name")
    val categoryGroupName: String,
    @JsonProperty("phone")
    val phone: String,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("road_address_name")
    val roadAddressName: String,
    @JsonProperty("x")
    val x: String, // 경도 (longitude)
    @JsonProperty("y")
    val y: String, // 위도 (latitude)
    @JsonProperty("place_url")
    val placeUrl: String,
    @JsonProperty("distance")
    val distance: String? // 중심좌표까지의 거리 (미터)
)

/**
 * KakaoSearchResponse를 KakaoPlaceListResult로 변환하는 확장 함수
 */
fun KakaoSearchResponse.toProto(): KakaoPlaceListResult {
    return KakaoPlaceListResult.newBuilder()
        .addAllPlaces(documents.map { it.toProto() })
        .setMeta(meta.toProto())
        .build()
}

fun Meta.toProto(): KakaoPlaceMeta {
    return KakaoPlaceMeta.newBuilder()
        .setTotalCount(this.totalCount)
        .setPageableCount(this.pageableCount)
        .setIsEnd(this.isEnd)
        .build()
}

/**
 * Document를 KakaoPlace로 변환하는 확장 함수
 */
fun Document.toProto(): KakaoPlace {
    return KakaoPlace.newBuilder()
        .setId(id)
        .setName(placeName)
        .setCategoryName(categoryName)
        .setCategoryGroupCode(categoryGroupCode)
        .setCategoryGroupName(categoryGroupName)
        .setPhone(phone)
        .setAddressName(addressName)
        .setRoadAddressName(roadAddressName)
        .setX(x)
        .setY(y)
        .setUrl(placeUrl)
        .build()
}
