package com.example.echat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.screens.BottomNavigationIcon

class MainViewModel: ViewModel() {

    // Bottom navigation
   private val _selectedNavigationItemIndex = mutableStateOf<Int>(0)
    val selectedNavigationItemIndex = _selectedNavigationItemIndex

    fun setSelectedNavigationItemIndex(index: Int) {
        _selectedNavigationItemIndex.value = index
    }

    // Search bar
    private val _textState = mutableStateOf("")
    val textState = _textState

    // onUpdateText
    fun onUpdateText(newText: String) {
        _textState.value = newText
    }
}