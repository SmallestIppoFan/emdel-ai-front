package com.example.emdel.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.emdel.data.dto.ChatMessageRequest
import com.example.emdel.data.dto.ChatMessageResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.time.Duration

interface ChatApiService {
    suspend fun sendMessage(request: ChatMessageRequest): ChatMessageResponse
}

class ChatApiServiceImpl(
    private val baseUrl: String,
    private val context: Context? = null
) : ChatApiService {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            })
        }
        engine {
            config {
                connectTimeout(Duration.ofSeconds(20))
                readTimeout(Duration.ofSeconds(20))
                writeTimeout(Duration.ofSeconds(20))
                
                // Добавляем Chucker interceptor для отладки
                context?.let { ctx ->
                    addInterceptor(ChuckerInterceptor.Builder(ctx).build())
                }
            }
        }
    }

    override suspend fun sendMessage(request: ChatMessageRequest): ChatMessageResponse {
        return client.post("$baseUrl/api/chat") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}

