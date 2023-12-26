package com.example.echat.data.model

data class User(
    val username: String,
    val name: String? = null,
    val email: String,
    val bio: String = "Bio",
    val token: String,
)