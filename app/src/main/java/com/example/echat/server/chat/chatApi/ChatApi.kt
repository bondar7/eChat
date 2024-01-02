package com.example.echat.server.chat.chatApi

import com.echat_backend.data.requests.CreateSessionRequest
import com.example.echat.data.model.Person
import com.example.echat.server.chat.dto.MessageDTO
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @POST("create-session")
    suspend fun createSession(
        @Body request: CreateSessionRequest
    ): SessionResponse

    @GET("messages-by-sessionID")
    suspend fun getMessagesBySessionId(
        @Query("sessionId") sessionId: String
    ): List<MessageDTO>
}

@Serializable
data class SessionResponse(
    val sessionId: String
)