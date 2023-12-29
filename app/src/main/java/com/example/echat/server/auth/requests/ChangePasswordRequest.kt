package com.example.echat.server.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
val usernameToFindUser: String,
val newPassword: String
)
