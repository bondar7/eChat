package com.example.echat.ui.screens.chats_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.server.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(

): ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState


    fun onSearchTextStateChange(newText: String) {
        _searchTextState.value = newText
    }
}