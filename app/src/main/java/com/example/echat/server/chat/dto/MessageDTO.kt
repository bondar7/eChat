package com.example.echat.server.chat.dto

import com.example.echat.data.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDTO(
    val sessionId: String,
    val senderId: String,
    val senderUsername: String,
    val content: String,
    val timestamp: Long,
    val id: String
) {
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            content = content,
            formattedTime = formattedDate,
            username = senderUsername,
            senderId = senderId
        )
    }
}