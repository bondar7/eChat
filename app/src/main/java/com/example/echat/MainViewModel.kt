package com.example.echat

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.echat.auth.AuthRepository
import com.example.echat.data.model.User
import com.example.echat.ui.screens.BottomNavigationIcon
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: SharedPreferences
): ViewModel(
) {

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

    val user = loadUser()

  private fun loadUser(): User? {
        val gson = Gson()
        val userJson = prefs.getString("USER", null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }
}