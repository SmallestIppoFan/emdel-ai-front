package com.example.emdel.data.repository

import android.content.Context
import com.example.emdel.data.config.ApiConfig
import com.example.emdel.data.dto.ChatMessageRequest
import com.example.emdel.data.mapper.toDomain
import com.example.emdel.data.remote.ChatApiService
import com.example.emdel.data.remote.ChatApiServiceImpl
import com.example.emdel.domain.model.ChatMessage

interface ChatRepository {
    suspend fun sendMessage(text: String): Result<ChatMessage>
}

class ChatRepositoryImpl(
    private val context: Context? = null,
    private val apiService: ChatApiService = ChatApiServiceImpl(ApiConfig.BASE_URL, context)
) : ChatRepository {

    override suspend fun sendMessage(text: String): Result<ChatMessage> {
        return try {
            val request = ChatMessageRequest(text = text)
            val response = apiService.sendMessage(request)
            Result.success(response.toDomain())
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("Не удалось подключиться к серверу. Проверьте подключение к интернету и настройки API."))
        } catch (e: java.net.SocketTimeoutException) {
            Result.failure(Exception("Превышено время ожидания ответа от сервера."))
        } catch (e: io.ktor.client.network.sockets.ConnectTimeoutException) {
            Result.failure(Exception("Не удалось подключиться к серверу. Убедитесь, что сервер запущен."))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка при отправке сообщения: ${e.message ?: "Неизвестная ошибка"}"))
        }
    }
}

