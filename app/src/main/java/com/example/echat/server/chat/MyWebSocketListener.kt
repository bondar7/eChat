package com.example.echat.server.chat

import android.util.Log
import com.example.echat.MainViewModel
import com.example.echat.server.chat.dto.MessageDTO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import java.io.File

class MyWebSocketListener(
    private val chatViewModel: ChatViewModel,
) : WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
        val messageDto = Json.decodeFromString<MessageDTO>(text)
        val message = messageDto.toMessage()
        chatViewModel.receiveNewMessage(message)
        Log.d("NEW MESSAGE:", message.toString())
    }

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        // Викликається, коли WebSocket-з'єднання успішно встановлено
        println("WebSocket connection opened successfully")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        // Викликається в разі невдачі під час встановлення WebSocket-з'єднання
        println("WebSocket connection failed: ${t.message}")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        println("WebSocket connection closed successfully")
    }

    // Метод для відправлення повідомлення
    fun sendMessage(webSocket: WebSocket, message: String) {
        webSocket.send(message)
    }

    // Метод для відправлення картинки в байтах
    fun sendImageMessage(webSocket: WebSocket, image: ByteArray) {
        val byteString: ByteString = image.toByteString()
        webSocket.send(byteString)
    }

    // Method to send audio message in bytes
    fun sendAudioMessage(webSocket: WebSocket, audio: File) {
        Log.d("TRY TO SEND AUDIO (START)", audio.toString())
        val byteArray: ByteArray = audio.readBytes()
        val byteString: ByteString = byteArray.toByteString()
        webSocket.send(byteString)

        Log.d("SENT AUDIO: ", byteString.toString())
    }
}