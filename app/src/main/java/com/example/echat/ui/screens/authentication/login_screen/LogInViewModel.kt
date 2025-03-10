package com.example.echat.ui.screens.authentication.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echat.server.auth.repository.AuthRepository
import com.example.echat.server.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    private val _username = mutableStateOf("")
    val username = _username

    private val _password = mutableStateOf("")
    val password = _password

    fun onUpdateUsername(newText: String) {
        _username.value = newText
    }
    fun onUpdatePassword(newPw: String) {
        _password.value = newPw
    }

    private val _error = mutableStateOf("")
    val error = _error
    fun setError(error: String) {
        _error.value = error
    }

    fun logIn(keyboardController: SoftwareKeyboardController?) {
        viewModelScope.launch {
            _isLoading.value = true
            if (_username.value.isNotBlank() && _password.value.isNotBlank()) {
                val result = authRepository.logIn(_username.value, _password.value)
                authViewModel.resultChannel.send(result)
            } else {
                setError("Fields cannot be blank")
            }
            _isLoading.value = false
            keyboardController?.hide()
        }
    }
}