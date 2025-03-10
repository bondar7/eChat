package com.example.echat.server.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: String,
    val username: String,
    val name: String? = null,
    val email: String,
    val bio: String,
    val avatar: ByteArray? = null,
    val token: String
)