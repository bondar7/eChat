package com.example.echat.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
val usernameToFindUser: String,
val newPassword: String
)
