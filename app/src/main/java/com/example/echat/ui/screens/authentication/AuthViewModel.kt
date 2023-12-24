package com.example.echat.ui.screens.authentication

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
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

    private val _emailSignUp = mutableStateOf("")
    val emailSignUp = _emailSignUp
    fun onUpdateEmailSignUp(newText: String) {
        _emailSignUp.value = newText
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
                && _emailSignUp.value.isNotBlank()
                && signUpPassword.value.isNotBlank()
            ) {
                val result = authRepository.signUp(
                    _signUpUsername.value,
                    _emailSignUp.value,
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

    //
    private val _isUsernameAvailable = mutableStateOf("")
    val isUsernameAvailable = _isUsernameAvailable
    private val _isUsernameAvailableColor = mutableStateOf(Color.Black)
    val isUsernameAvailableColor = _isUsernameAvailableColor

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