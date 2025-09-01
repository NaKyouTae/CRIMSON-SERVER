package com.spectrum.crimson.common.converter

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.spectrum.crimson.domain.entity.RolePermissions
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

private val objectMapper = jacksonObjectMapper()

@Converter(autoApply = false)
class RolePermissionsJsonbConverter : AttributeConverter<RolePermissions, String> {
    override fun convertToDatabaseColumn(attribute: RolePermissions?): String {
        return try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException()
        }
    }

    override fun convertToEntityAttribute(dbData: String?): RolePermissions? {
        val typeReference: TypeReference<RolePermissions?> = object : TypeReference<RolePermissions?>() {}

        return try {
            objectMapper.readValue(dbData, typeReference)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}