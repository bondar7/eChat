package com.example.echat.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    fun changePassword(usernameToFindUser: String, newPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank() && newPassword.length > 8) {
                authRepository.changePassword(usernameToFindUser, newPassword)
            }
        }
    }

    // checking password
    val isPasswordCorrectLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun checkPassword(usernameToFindUser: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isPasswordCorrect = authRepository.checkPassword(usernameToFindUser, password)
            isPasswordCorrectLiveData.postValue(isPasswordCorrect)
        }
    }

    fun changeUsername(usernameToFindUser: String, newUsername: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank() && newUsername.length > 3) {
                authRepository.changeUsername(usernameToFindUser, newUsername)
            }
        }
    }

    fun changeName(usernameToFindUser: String, newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank() && newName.length > 3) {
                authRepository.changeName(usernameToFindUser, newName)
            }
        }
    }

    // checking username
    private val _isUsernameAvailableState: MutableState<Boolean?> = mutableStateOf(null)
    val isUsernameAvailable: MutableState<Boolean?> = _isUsernameAvailableState
    fun checkUsername(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isAvailable = authRepository.checkUsername(username)
            _isUsernameAvailableState.value = isAvailable
        }
    }

    fun changeEmail(usernameToFindUser: String, newEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank() && newEmail.isNotBlank()) {
                authRepository.changeEmail(usernameToFindUser, newEmail)
            }
        }
    }

    fun changeUserBio(usernameToFindUser: String, newBio: String = "Bio") {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank()) {
                authRepository.changeUserBio(usernameToFindUser, newBio)
            }
        }
    }

    fun authenticate() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.authenticate()
            resultChannel.send(result)
        }
    }

}