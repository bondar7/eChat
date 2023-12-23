package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)