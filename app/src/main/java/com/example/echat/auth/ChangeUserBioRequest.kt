package com.example.echat.auth

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUserBioRequest(
    val usernameToFindUser: String,
    val newBio: String
)