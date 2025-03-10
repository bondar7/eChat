package com.example.echat.server.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: String,
    val username: String,
    val name: String,
    val avatar: ByteArray?,
    val bio: String
)