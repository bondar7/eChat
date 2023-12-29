package com.example.echat.server.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeEmailRequest(
    val usernameToFindUser: String,
    val newEmail: String
)
