package com.example.echat.ui.screens.authentication.signup_screen

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echat.auth.repository.AuthRepository
import com.example.echat.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    // is loading
    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    // fields
    private val _username = mutableStateOf("")
    val username = _username

    private val _email = mutableStateOf("")
    val email = _email

    private val _password = mutableStateOf("")
    val password = _password
    fun onUpdateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun onUpdateUsername(newText: String) {
        _username.value = newText
    }
    fun onUpdatePassword(newPw: String) {
        _password.value = newPw
    }

    // errors
    private val _usernameError = mutableStateOf("")
    private val _emailError = mutableStateOf("")
    private val _pwError = mutableStateOf("")
    val usernameError = _usernameError
    val emailError = _emailError
    val pwError = _pwError

    // functions
    fun signUp() {
        viewModelScope.launch {
            _isLoading.value = !_isLoading.value
            delay(300)
            _isLoading.value = !_isLoading.value
            checkUsername(_username.value)
            checkEmail(_email.value)
            checkPassword(_password.value)

            if (
                _usernameError.value.isBlank()
                && _emailError.value.isBlank()
                && _pwError.value.isBlank()
            ) {
                _isLoading.value = !_isLoading.value
                val result = authRepository.signUp(
                    _username.value,
                    _email.value,
                    _password.value
                )
                authViewModel.resultChannel.send(result)
                _isLoading.value = !_isLoading.value
            }
        }
    }
    private fun checkUsername(username: String) {
        val usernameIsTooShortError = "Username must contains at least 4 characters"
        val usernameIsNotAvailableError = "Username is not available"
        if (username.length < 4) {
            _usernameError.value = usernameIsTooShortError
        } else {
            viewModelScope.launch {
                val response = authRepository.checkUsername(username)
                _usernameError.value = ""
                if (!response) {
                    _usernameError.value = usernameIsNotAvailableError
                }
            }
        }
    }
    private fun checkEmail(email: String) {
        val emailIsNotAvailableError = "Email is not available"
        val emailIsNotValidError = "Email is not valid"
        val emailIsBlank = "Email field cannot be blank"
        if (email.isNotBlank()) {
            if (isValidEmail(email)) {
                viewModelScope.launch {
                    val response = authRepository.checkEmail(email)
                    _emailError.value = ""
                    if (!response) {
                        _emailError.value = emailIsNotAvailableError
                    }
                }
            } else {
                _emailError.value = emailIsNotValidError
            }
        } else {
            _emailError.value = emailIsBlank
        }
    }
    private fun checkPassword(password: String) {
        val pwIsTooShortError = "Password must contains at least 9 characters"
        if (password.length <= 8) {
            _pwError.value = pwIsTooShortError
        } else {
            _pwError.value = ""
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}