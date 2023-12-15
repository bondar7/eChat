package com.example.echat.screens.authentication_screens.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _loginTextState = mutableStateOf("")
    val loginTextState = _loginTextState
    fun onUpdateLoginText(newText: String) {
        _loginTextState.value = newText
    }
    private val _phoneTextState = mutableStateOf("")
    val phoneTextState = _phoneTextState
    fun onUpdatePhoneText(newText: String) {
        _phoneTextState.value = newText
    }
    private val _passwordTextState = mutableStateOf("")
    val passwordTextState = _passwordTextState
    fun onUpdatePasswordText(newText: String) {
        _passwordTextState.value = newText
    }
}