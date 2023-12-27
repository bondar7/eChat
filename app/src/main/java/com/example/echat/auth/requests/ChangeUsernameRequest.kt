package com.example.echat.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUsernameRequest(
    val usernameToFindUser: String,
    val newUsername: String
)