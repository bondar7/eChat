package com.example.echat.server.chat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.echat.EChatApp
import com.example.echat.MainViewModel
import com.example.echat.communication.audio.player.AudioPlayer
import com.example.echat.communication.audio.recorder.AudioRecorder
import com.example.echat.navigation.Screen
import com.example.echat.server.data.model.Message
import com.example.echat.server.chat.repository.ChatRepository
import com.example.echat.server.data.model.Person
import com.example.echat.server.session.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.WebSocket
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sessionRepository: SessionRepository,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer
) : ViewModel() {
    private val myWebSocketListener = MyWebSocketListener(this@ChatViewModel)

    private var webSocket: MutableState<WebSocket?> = mutableStateOf(null)

    private val _state = mutableStateOf(ChatState())
    val state = _state

    private val _selectedSessionId = mutableStateOf("")

    private val _selectedUser: MutableState<Person?> = mutableStateOf(null)
    val selectedUser = _selectedUser
    fun updateSelectedUser(newUser: Person) {
        _selectedUser.value = newUser
    }

    private val audioFile: MutableState<File?> = mutableStateOf(null)

    private val _neededPermissions: MutableState<Array<String>> = mutableStateOf(emptyArray())
    val neededPermissions = _neededPermissions

    private val _isRecordingAudio: MutableState<Boolean> = mutableStateOf(false)
    val isRecordingAudio = _isRecordingAudio

    fun startChat(user1Id: String, currentUserId: String, navController: NavHostController) {
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
            withContext(Dispatchers.Main) {
                Log.d("SELECTED USER:", _selectedUser.value?.username.toString())
                navController.navigate(Screen.ChatScreen.route) {
                    popUpTo(Screen.DetailedUserScreen.route) {
                        inclusive = true
                    }
                }
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

    fun sendImageMessage(image: ByteArray) {
        val socket = webSocket.value
        if (socket != null) {
            CoroutineScope(Dispatchers.IO).launch {
                myWebSocketListener.sendImageMessage(socket, image)
            }
        }
    }

    fun sendAudioMessage() {
        stopRecording()
        val socket = webSocket.value
        val audioFile = audioFile.value
        Log.d("AUDIO FILE IS NULL?", audioFile.toString())
        if (socket != null && audioFile != null) {
            CoroutineScope(Dispatchers.IO).launch {
                myWebSocketListener.sendAudioMessage(socket, audioFile)
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
    
    fun closeChat() {
        webSocket.value?.close(1000, "User left the chat")
        updateSelectedSessionId("")
    }
    
    fun setWebSocket(socket: WebSocket) {
        webSocket.value = socket
    }

    fun updateSelectedSessionId(newSessionId: String) {
        _selectedSessionId.value = newSessionId
    }

    // Audio
    fun startRecording(context: Context) {
       val neededPermission = context.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
        if(neededPermission == PackageManager.PERMISSION_GRANTED) {
            File(context.applicationContext.cacheDir, "audio.mp3").also {
                _isRecordingAudio.value = true
                audioRecorder.start(it)
                audioFile.value = it
            }
        } else {
            _neededPermissions.value = arrayOf(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }
    fun stopRecording() {
        audioRecorder.stop()
        _isRecordingAudio.value = false
    }

    fun playAudio(audioData: ByteArray) {
        audioPlayer.play(audioData)
    }

    fun stopAudio() {
        audioPlayer.stop()
    }
}