package com.example.echat.server.session.repository

import android.util.Log
import com.echat_backend.data.requests.CreateSessionRequest
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.server.data.model.ChatSession
import com.example.echat.server.session.sessionApi.SessionApi
import retrofit2.HttpException

class SessionRepositoryImpl(
    private val api: SessionApi
): SessionRepository {
    override suspend fun getSession(
        user1Id: String,
        user2Id: String,
        chatViewModel: ChatViewModel
    ) {
        try {
            val request = CreateSessionRequest(user1Id, user2Id)
            val response = api.createSession(request)

            Log.d("RECEIVED SESSION ID", response.sessionId)
            chatViewModel.updateSelectedSessionId(response.sessionId)
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getSessionsByUserId(userId: String): List<ChatSession> {
       return try {
            val response = api.getSessionsByUserId(userId)
           if (response != null) {
               Log.d(" RESPONSE:", response.toString())
           } else {
               Log.d(" RESPONSE:", "Chat sessions are null")
           }
            response
        } catch (e: HttpException) {
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}