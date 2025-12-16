package com.example.emdel.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageResponse(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long,
    val clinics: List<ClinicDto>? = null
)
