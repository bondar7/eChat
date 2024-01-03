package com.example.echat.server.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatSession(
    val sessionId: String,
    val user: Person,
    val lastMessage: String
)