package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val username: String,
    val phoneNumber: String,
    val bio: String,
    val token: String
)