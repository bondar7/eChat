package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class LogInRequest(
    val username: String,
    val password: String,
)
