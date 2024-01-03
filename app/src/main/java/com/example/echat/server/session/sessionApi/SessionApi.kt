package com.example.echat.server.session.sessionApi

import com.echat_backend.data.requests.CreateSessionRequest
import com.example.echat.server.data.model.ChatSession
import com.example.echat.server.session.responses.CreateChatSessionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SessionApi {
    @POST("create-session")
    suspend fun createSession(
        @Body request: CreateSessionRequest
    ): CreateChatSessionResponse

   @GET("get-sessions-by-userID")
   suspend fun getSessionsByUserId(
       @Query("userId") userId: String
   ): List<ChatSession>
}