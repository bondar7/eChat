package com.example.echat.server.chat

import com.example.echat.data.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)