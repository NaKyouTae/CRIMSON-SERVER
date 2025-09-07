# 카카오 로컬 API 검색 사용법

이 문서는 `KakaoSearchByKeywordPort`를 사용하여 카카오 로컬 API로 장소를 검색하는 방법을 설명합니다.

## 구현된 기능

### 1. 기본 키워드 검색
```kotlin
// 간단한 키워드 검색
val result = kakaoSearchByKeywordPort.searchByKeyword("스타벅스")

// 페이지와 크기 지정
val result = kakaoSearchByKeywordPort.searchByKeyword("맛집", page = 2, size = 10)
```

### 2. 좌표 기반 검색
```kotlin
// 특정 좌표 주변에서 검색
val result = kakaoSearchByKeywordPort.searchByKeywordWithLocation(
    query = "카페",
    longitude = "127.027619",  // 경도
    latitude = "37.497952",    // 위도
    radius = 1000,             // 1km 반경
    sort = "distance"          // 거리순 정렬
)
```

### 3. 상세 파라미터 검색
```kotlin
val request = KakaoSearchRequest(
    query = "치킨",
    page = 1,
    size = 15,
    x = "127.027619",
    y = "37.497952",
    radius = 2000,
    sort = "distance",
    categoryGroupCode = "FD6"  // 음식점 카테고리
)

val result = kakaoSearchByKeywordPort.searchByKeyword(request)
```

## REST API 엔드포인트

### 1. 기본 키워드 검색
```http
GET /api/v1/kakao/search/keyword?query=스타벅스&page=1&size=15
```

### 2. 좌표 기반 검색
```http
GET /api/v1/kakao/search/keyword/location?query=카페&longitude=127.027619&latitude=37.497952&radius=1000&sort=distance
```

### 3. 상세 파라미터 검색
```http
POST /api/v1/kakao/search/keyword
Content-Type: application/json

{
  "query": "치킨",
  "page": 1,
  "size": 15,
  "x": "127.027619",
  "y": "37.497952",
  "radius": 2000,
  "sort": "distance",
  "categoryGroupCode": "FD6"
}
```

## 응답 형식

```json
{
  "meta": {
    "totalCount": 100,
    "pageableCount": 45,
    "isEnd": false,
    "sameName": {
      "region": ["서울특별시", "강남구"],
      "keyword": "스타벅스",
      "selectedRegion": "서울특별시 강남구"
    }
  },
  "documents": [
    {
      "id": "1234567890",
      "placeName": "스타벅스 강남점",
      "categoryName": "음식점 > 카페 > 스타벅스",
      "categoryGroupCode": "CE7",
      "categoryGroupName": "카페",
      "phone": "02-1234-5678",
      "addressName": "서울 강남구 테헤란로 123",
      "roadAddressName": "서울 강남구 테헤란로 123",
      "x": "127.027619",
      "y": "37.497952",
      "placeUrl": "http://place.map.kakao.com/1234567890",
      "distance": "500"
    }
  ]
}
```

## 카테고리 그룹 코드

- `MT1`: 대형마트
- `CS2`: 편의점
- `PS3`: 어린이집, 유치원
- `SC4`: 학교
- `AC5`: 학원
- `PK6`: 주차장
- `OL7`: 주유소, 충전소
- `SW8`: 지하철역
- `BK9`: 은행
- `CT1`: 문화시설
- `AG2`: 중개업소
- `PO3`: 공공기관
- `AT4`: 관광명소
- `AD5`: 숙박
- `FD6`: 음식점
- `CE7`: 카페
- `HP8`: 병원
- `PM9`: 약국

## 에러 처리

API 호출 실패 시 `KakaoApiException`이 발생합니다:

```kotlin
try {
    val result = kakaoSearchByKeywordPort.searchByKeyword("검색어")
} catch (e: KakaoApiException) {
    // 에러 처리
    logger.error("카카오 API 호출 실패: ${e.message}", e)
}
```

## 설정

`application.yml`에서 카카오 API 설정을 확인하세요:

```yaml
kakao:
  api:
    rest-api-key: d8b2a3d2c19bfe33c54c19c015ea2726
    base-url: https://dapi.kakao.com
```

## 주의사항

1. **API 키 보안**: REST API 키는 환경 변수로 관리하는 것을 권장합니다.
2. **요청 제한**: 카카오 API는 일일 요청 제한이 있습니다.
3. **좌표계**: 카카오 API는 WGS84 좌표계를 사용합니다.
4. **거리 단위**: radius 파라미터는 미터(m) 단위입니다.
