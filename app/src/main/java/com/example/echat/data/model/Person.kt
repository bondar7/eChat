package com.example.echat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val username: String,
    val name: String,
    val avatar: ByteArray? = null,
    val bio: String
)
