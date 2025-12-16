package com.example.emdel.chat.presentation

sealed class ChatIntent {
    data class SendMessage(val text: String) : ChatIntent()
    data class UpdateInputText(val text: String) : ChatIntent()
    object RetryLastMessage : ChatIntent()
    object ClearError : ChatIntent()
}

