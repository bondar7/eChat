package com.example.echat.data.model

data class User(
    val username: String,
    val phoneNumber: String,
    val bio: String = "Bio",
    val token: String,
)