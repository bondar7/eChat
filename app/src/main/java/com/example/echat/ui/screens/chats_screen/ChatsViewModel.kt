package com.example.echat.ui.screens.chats_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.MainViewModel
import com.example.echat.server.data.model.ChatSession
import com.example.echat.server.main.repository.MainRepository
import com.example.echat.server.session.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val mainViewModel: MainViewModel
): ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState
    fun onSearchTextStateChange(newText: String) {
        _searchTextState.value = newText
    }

    private val _chatSessions = mutableStateOf(emptyList<ChatSession>())
    val chatSessions = _chatSessions

    init {
        getSessionsByUserId()
    }

   private fun getSessionsByUserId() {
        val currentUserId = mainViewModel.user.value?.id
        if (currentUserId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val chatSessions = sessionRepository.getSessionsByUserId(currentUserId)
                _chatSessions.value = chatSessions
            }
        }
    }
}