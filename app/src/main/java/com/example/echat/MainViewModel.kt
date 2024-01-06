package com.example.echat

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.server.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: SharedPreferences,
) : ViewModel() {

    private val _user = mutableStateOf(loadUser())
    val user = _user
    fun updateUser(user: User) {
        _user.value = user
    }

    private val _isAvatarLoading = mutableStateOf(false)
    val isAvatarLoading = _isAvatarLoading
    fun setIsLoadingAvatar(isLoading: Boolean) {
        _isAvatarLoading.value = isLoading
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