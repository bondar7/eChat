package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val username: String,
    val email: String,
    val bio: String,
    val token: String
)