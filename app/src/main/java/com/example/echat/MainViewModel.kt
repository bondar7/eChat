package com.example.echat

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: SharedPreferences
): ViewModel(
) {

    // Search bar
    private val _textState = mutableStateOf("")
    val textState = _textState

    // onUpdateText
    fun onUpdateText(newText: String) {
        _textState.value = newText
    }

    private val _user = mutableStateOf(loadUser())
    val user = _user
    fun updateUser(user: User) {
        _user.value = user
    }

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