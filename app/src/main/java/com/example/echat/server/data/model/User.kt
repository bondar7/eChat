package com.example.echat.server.data.model

data class User(
    val id: String,
    val username: String,
    val name: String? = null,
    val email: String,
    val bio: String = "Bio",
    val avatar: ByteArray? = null,
    val token: String,
)