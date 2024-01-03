package com.example.echat.server.chat.chatApi

import com.echat_backend.data.requests.CreateSessionRequest
import com.example.echat.server.data.model.Person
import com.example.echat.server.chat.dto.MessageDTO
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @GET("get-messages-by-sessionID")
    suspend fun getMessagesBySessionId(
        @Query("sessionId") sessionId: String
    ): List<MessageDTO>
}