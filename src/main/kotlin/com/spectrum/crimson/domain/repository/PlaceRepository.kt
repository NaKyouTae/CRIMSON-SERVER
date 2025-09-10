package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : JpaRepository<Place, String> {
    fun findByPlaceGroupId(placeGroupId: String): List<Place>
}
