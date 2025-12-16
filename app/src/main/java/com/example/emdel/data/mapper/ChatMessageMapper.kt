package com.example.emdel.data.mapper

import com.example.emdel.data.dto.ChatMessageRequest
import com.example.emdel.data.dto.ChatMessageResponse
import com.example.emdel.data.dto.ClinicDto
import com.example.emdel.domain.model.ChatMessage
import com.example.emdel.domain.model.Clinic

fun ChatMessageResponse.toDomain(): ChatMessage {
    return ChatMessage(
        id = this.id,
        text = this.text,
        isFromUser = this.isFromUser,
        timestamp = this.timestamp,
        clinics = this.clinics?.map { it.toDomain() }
    )
}

fun ClinicDto.toDomain(): Clinic {
    return Clinic(
        name = this.name,
        address = this.address,
        rating = this.rating,
        whatsapp = this.whatsapp,
        workingHours = this.work_hoursOrNull()
    )
}

// В DTO поле названо working_hours, маппим его в workingHours
private fun ClinicDto.work_hoursOrNull(): String? = this.working_hours

fun ChatMessage.toRequest(): ChatMessageRequest {
    return ChatMessageRequest(
        text = this.text
    )
}
