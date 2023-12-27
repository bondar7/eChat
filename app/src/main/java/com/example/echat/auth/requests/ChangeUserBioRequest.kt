package com.example.echat.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUserBioRequest(
    val usernameToFindUser: String,
    val newBio: String
)