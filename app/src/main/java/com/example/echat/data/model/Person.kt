package com.example.echat.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val username: String,
    val name: String,
    val avatar: ByteArray?,
    val bio: String
)