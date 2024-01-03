package com.example.echat.server.session.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatSessionResponse(
    val sessionId: String
)