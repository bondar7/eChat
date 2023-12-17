package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
    val phoneNumber: String,
    val password: String
)