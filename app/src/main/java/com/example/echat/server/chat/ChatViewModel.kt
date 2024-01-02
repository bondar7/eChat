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
import com.example.echat.data.model.Message
import com.example.echat.server.auth.repository.AuthRepository
import com.example.echat.server.chat.repository.ChatRepository
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
) : ViewModel() {

    private val myWebSocketListener = MyWebSocketListener(this@ChatViewModel)

    private var webSocket: MutableState<WebSocket?> = mutableStateOf(null)

    fun setWebSocket(socket: WebSocket) {
        webSocket.value = socket
    }

    private val _selectedSessionId = mutableStateOf("")
    val selectedSessionId = _selectedSessionId
    fun updateSelectedSessionId(newSessionId: String) {
        _selectedSessionId.value = newSessionId
    }

    private val _state = mutableStateOf(ChatState())
    val state = _state
    fun receiveNewMessage(message: Message) {
        val updatedList = _state.value.messages.toMutableList()
            .apply { add(0, message) }
        _state.value = _state.value.copy(messages = updatedList)
    }

    fun getMessagesBySessionId() {
        if (_selectedSessionId.value.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                _state.value = _state.value.copy(isLoading = true)
                val messages = chatRepository.getMessagesBySessionId(
                    _selectedSessionId.value,
                    this@ChatViewModel
                )
                _state.value = _state.value.copy(messages = messages, isLoading = true)
                Log.d("RECEIVED MESSAGES: ", _state.value.messages.toString())
            }
        }
    }

    fun sendMessage(message: String) {
        val socket = webSocket.value
        if (socket != null) {
            CoroutineScope(Dispatchers.IO).launch {
                myWebSocketListener.sendMessage(socket, message)
            }
        }
    }

    fun createSession(user1Id: String, user2Id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            chatRepository.getSession(user1Id, user2Id, this@ChatViewModel)
        }
    }

    fun connectToWebSocket(currentUserId: String, currentSessionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            chatRepository.connectToWebSocket(currentUserId, currentSessionId, this@ChatViewModel)
        }
    }
}