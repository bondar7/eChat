package com.example.echat.server.chat.repository

import android.content.SharedPreferences
import android.util.Log
import com.echat_backend.data.requests.CreateSessionRequest
import com.example.echat.MainViewModel
import com.example.echat.server.data.model.Message
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.server.chat.MyWebSocketListener
import com.example.echat.server.chat.chatApi.ChatApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException

class ChatRepositoryImpl(
    private val api: ChatApi,
) : ChatRepository {
    override suspend fun connectToWebSocket(
        currentUserId: String,
        currentSessionId: String,
        chatViewModel: ChatViewModel,
    ) {
        Log.d("currentUserId:", currentUserId)
        Log.d("currentSessionId:", currentSessionId)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${ChatRepository.BASE_URL}/chat/$currentSessionId/$currentUserId").build()
        val websocket = client.newWebSocket(request, MyWebSocketListener(chatViewModel))
        chatViewModel.setWebSocket(websocket)
    }

    override suspend fun getMessagesBySessionId(sessionId: String, chatViewModel: ChatViewModel): List<Message> {
        return try {
            val messagesDTO = api.getMessagesBySessionId(sessionId)
            val messages = messagesDTO.map {
                it.toMessage()
            }
            messages
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("(HTTP EXCEPTION)", e.toString())
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("(EXCEPTION)", e.toString())
            emptyList()
        }
    }
}