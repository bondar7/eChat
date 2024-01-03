package com.example.echat.server.chat

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.echat.MainViewModel
import com.example.echat.server.data.model.Message
import com.example.echat.server.auth.repository.AuthRepository
import com.example.echat.server.chat.repository.ChatRepository
import com.example.echat.server.session.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val myWebSocketListener = MyWebSocketListener(this@ChatViewModel)

    private var webSocket: MutableState<WebSocket?> = mutableStateOf(null)

    private val _state = mutableStateOf(ChatState())
    val state = _state

    private val _selectedSessionId = mutableStateOf("")


    fun startChat(user1Id: String, currentUserId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // create or get existing session
            sessionRepository.getSession(
                user1Id,
                currentUserId,
                this@ChatViewModel
            )

            // receive all messages for selected session
            getMessagesBySessionId()

            // connect to web socket
            if (_selectedSessionId.value.isNotBlank()) {
                chatRepository.connectToWebSocket(
                    currentUserId,
                    _selectedSessionId.value,
                    this@ChatViewModel
                )
            }
        }
    }

    fun receiveNewMessage(message: Message) {
        val updatedList = _state.value.messages.toMutableList()
            .apply { add(message) }
        _state.value = _state.value.copy(messages = updatedList)
    }

    fun sendMessage(message: String) {
        val socket = webSocket.value
        if (socket != null) {
            CoroutineScope(Dispatchers.IO).launch {
                myWebSocketListener.sendMessage(socket, message)
            }
        }
    }

    private fun getMessagesBySessionId() {
        if (_selectedSessionId.value.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                _state.value = _state.value.copy(isLoading = true)
                val messages = chatRepository.getMessagesBySessionId(
                    _selectedSessionId.value,
                    this@ChatViewModel
                )
                _state.value = _state.value.copy(messages = messages, isLoading = false)
            }
        }
    }


    fun setWebSocket(socket: WebSocket) {
        webSocket.value = socket
    }

    fun updateSelectedSessionId(newSessionId: String) {
        _selectedSessionId.value = newSessionId
    }
}