package com.example.echat.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
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

    // errors password
    private val _pwError1 = mutableStateOf("")
    private val _pwError2 = mutableStateOf("")
    val pwError1 = _pwError1
    val pwError2 = _pwError2
    fun updatePwError2(newText: String) {
        _pwError2.value = newText
    }
    fun updatePwError1(newText: String) {
        _pwError1.value = newText
    }

    // email error
    private val _emailError = mutableStateOf("")
    val emailError = _emailError
    fun updateEmailError(newText: String) {
        _emailError.value = newText
    }


    fun logIn() {
        viewModelScope.launch {
            if (_logInUsername.value.isNotBlank() && _logInPassword.value.isNotBlank()) {
                val result = authRepository.logIn(_logInUsername.value, _logInPassword.value)
                resultChannel.send(result)
            }
        }
    }

    fun changePassword(usernameToFindUser: String, newPassword: String) {
        viewModelScope.launch {
            if (usernameToFindUser.isNotBlank() && newPassword.length > 8) {
                authRepository.changePassword(usernameToFindUser, newPassword)
            }
        }
    }

    // checking password
    val isPasswordCorrectLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun checkPassword(usernameToFindUser: String, password: String) {
        viewModelScope.launch {
            val isPasswordCorrect = authRepository.checkPassword(usernameToFindUser, password)
            isPasswordCorrectLiveData.postValue(isPasswordCorrect)
        }
    }

    fun changeUsername(usernameToFindUser: String, newUsername: String) {
        viewModelScope.launch {
            if (usernameToFindUser.isNotBlank() && newUsername.length > 3) {
                authRepository.changeUsername(usernameToFindUser, newUsername)
            }
        }
    }

    // checking username
    private val _isUsernameAvailableState: MutableState<Boolean?> = mutableStateOf(null)
    val isUsernameAvailable: MutableState<Boolean?> = _isUsernameAvailableState
    fun checkUsername(username: String) {
        viewModelScope.launch {
            val isAvailable = authRepository.checkUsername(username)
            _isUsernameAvailableState.value = isAvailable
        }
    }

    fun changeEmail(usernameToFindUser: String, newEmail: String) {
        viewModelScope.launch {
            if (usernameToFindUser.isNotBlank() && newEmail.isNotBlank()) {
                authRepository.changeEmail(usernameToFindUser, newEmail)
            }
        }
    }

    fun changeUserBio(usernameToFindUser: String, newBio: String = "Bio") {
        viewModelScope.launch {
            if (usernameToFindUser.isNotBlank()) {
                authRepository.changeUserBio(usernameToFindUser, newBio)
            }
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            val result = authRepository.authenticate()
            resultChannel.send(result)
        }
    }

}