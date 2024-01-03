package com.example.echat.server.chat.repository

import com.example.echat.server.data.model.Message
import com.example.echat.server.chat.ChatViewModel

interface ChatRepository {
  suspend fun connectToWebSocket(currentUserId: String, currentSessionId: String, chatViewModel: ChatViewModel)

  suspend fun getMessagesBySessionId(sessionId: String, chatViewModel: ChatViewModel): List<Message>
  companion object {
    const val BASE_URL = "ws://192.168.1.2:8080"
  }
}