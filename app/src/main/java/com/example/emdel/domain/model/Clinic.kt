package com.example.emdel.domain.model

data class Clinic(
    val name: String,
    val address: String,
    val rating: Double? = null,
    val whatsapp: String? = null,
    val workingHours: String? = null
)


