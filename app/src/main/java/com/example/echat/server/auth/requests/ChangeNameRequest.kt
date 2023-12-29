package com.example.echat.server.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNameRequest(
    val usernameToFindUser: String,
    val newName: String
)