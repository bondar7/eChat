package com.example.echat.data.model

data class Message(
    val content: String,
    val formattedTime: String,
    val username: String,
    val senderId: String
)
