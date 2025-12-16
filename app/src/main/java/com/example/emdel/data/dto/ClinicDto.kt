package com.example.emdel.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClinicDto(
    val name: String,
    val address: String,
    val rating: Double? = null,
    val whatsapp: String? = null,
    val working_hours: String? = null
)


