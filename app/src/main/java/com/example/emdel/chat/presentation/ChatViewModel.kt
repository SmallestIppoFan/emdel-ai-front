package com.example.emdel.chat.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.emdel.data.repository.ChatRepository
import com.example.emdel.data.repository.ChatRepositoryImpl
import com.example.emdel.domain.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    application: Application,
    private val repository: ChatRepository = ChatRepositoryImpl(context = application.applicationContext)
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    fun handleIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> sendMessage(intent.text)
            is ChatIntent.UpdateInputText -> updateInputText(intent.text)
            is ChatIntent.RetryLastMessage -> retryLastMessage()
            is ChatIntent.ClearError -> clearError()
        }
    }
    
    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = ChatMessage(
            text = text.trim(),
            isFromUser = true
        )

        _state.update { currentState ->
            currentState.copy(
                messages = currentState.messages + userMessage,
                inputText = "",
                isLoading = true
            )
        }

        viewModelScope.launch {
            repository.sendMessage(text).fold(
                onSuccess = { aiMessage ->
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + aiMessage,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = exception.message ?: "Ошибка при отправке сообщения"
                        )
                    }
                }
            )
        }
    }

    private fun updateInputText(text: String) {
        _state.update { it.copy(inputText = text) }
    }

    private fun retryLastMessage() {
        val lastUserMessage = _state.value.messages.lastOrNull { it.isFromUser }
        if (lastUserMessage != null) {
            // Удаляем последнее сообщение пользователя и ответ AI (если есть)
            val lastUserIndex = _state.value.messages.indexOfLast { it.isFromUser }
            val messagesWithoutLast = _state.value.messages.take(lastUserIndex)
            
            _state.update { 
                it.copy(
                    messages = messagesWithoutLast,
                    error = null
                ) 
            }
            sendMessage(lastUserMessage.text)
        }
    }
}

