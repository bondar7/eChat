package com.example.echat.ui.screens.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echat.auth.AuthRepository
import com.example.echat.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // result channel
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    // signing up
    private val _signUpUsername = mutableStateOf("")
    val signUpUsername = _signUpUsername
    fun onUpdateSignUpUsername(newText: String) {
        _signUpUsername.value = newText
    }

    private val _phoneSignUp = mutableStateOf("")
    val phoneSignUp = _phoneSignUp
    fun onUpdatePhoneSignUp(newText: String) {
        _phoneSignUp.value = newText
    }

    private val _signUpPassword = mutableStateOf("")
    val signUpPassword = _signUpPassword
    fun onUpdateSignUpPassword(newText: String) {
        _signUpPassword.value = newText
    }

    fun signUp() {
        viewModelScope.launch {
            if (
                _signUpUsername.value.isNotBlank()
                && _phoneSignUp.value.isNotBlank()
                && signUpPassword.value.isNotBlank()
            ) {
                val result = authRepository.signUp(
                    _signUpUsername.value,
                    _phoneSignUp.value,
                    signUpPassword.value
                )
                resultChannel.send(result)
            }
        }
    }

    // logging in
    private val _logInUsername = mutableStateOf("")
    val logInUsername = _logInUsername
    fun onUpdateLogInUsername(newText: String) {
        _logInUsername.value = newText
    }
    private val _logInPhone = mutableStateOf("")
    val logInPhone = _logInPhone
    fun onUpdateLogInPhone(newText: String) {
        _logInPhone.value = newText
    }
    private val _logInPassword = mutableStateOf("")
    val logInPassword = _logInPassword
    fun onUpdateLogInPassword(newText: String) {
        _logInPassword.value = newText
    }

    fun logIn() {
        viewModelScope.launch {
            if (_logInUsername.value.isNotBlank() && _logInPassword.value.isNotBlank()) {
                val result = authRepository.logIn(_logInUsername.value, _logInPassword.value)
                resultChannel.send(result)
            }
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            val result = authRepository.authenticate()
            resultChannel.send(result)
        }
    }

}