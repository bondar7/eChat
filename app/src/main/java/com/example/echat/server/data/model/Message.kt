package com.example.echat.server.data.model

data class Message(
    val text: String?,
    val image: ByteArray?,
    val formattedTime: String,
    val username: String,
    val senderId: String
)
