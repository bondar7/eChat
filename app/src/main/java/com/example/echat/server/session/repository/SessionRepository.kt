package com.example.echat.server.session.repository

import com.example.echat.server.chat.ChatViewModel
import com.example.echat.server.data.model.ChatSession

interface SessionRepository {
    suspend fun getSession(user1Id: String, user2Id: String, chatViewModel: ChatViewModel)

    suspend fun getSessionsByUserId(userId: String): List<ChatSession>
}