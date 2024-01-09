package com.example.echat.server.chat.dto

import com.example.echat.server.data.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class MessageDTO(
    val sessionId: String,
    val senderId: String,
    val senderUsername: String,
    val text: String?,
    val image: ByteArray?,
    val audio: ByteArray?,
    val timestamp: Long,
    val id: String
) {
    fun toMessage(): Message {
        val date = Date(timestamp)

        // Визначаємо шаблон для отримання часу (HH:mm)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Конвертуємо об'єкт Date до рядка за допомогою визначеного шаблону
        val formattedTime = timeFormat.format(date)

        return Message(
            text = text,
            image = image,
            audio = audio,
            formattedTime = formattedTime,
            username = senderUsername,
            senderId = senderId
        )
    }
}