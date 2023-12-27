package com.example.echat.auth

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.echat.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // is loading for all screens
    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

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


    // USERNAME FUNCTIONALITY .
    // is available
    private val _isUsernameAvailableState: MutableState<Boolean?> = mutableStateOf(null)
    val isUsernameAvailable: MutableState<Boolean?> = _isUsernameAvailableState
    fun setUsernameAvailableAsNull() {
        _isUsernameAvailableState.value = null
    }

    // errors
    private val _usernameError: MutableState<String> = mutableStateOf("")
    val usernameError: MutableState<String> = _usernameError

    // functions
    private fun checkUsername(username: String) {
        val tooShortError = "Username must contain at least 4 characters"
        CoroutineScope(Dispatchers.IO).launch {
            if (username.length >= 4) {
                val isAvailable = authRepository.checkUsername(username)
                _isUsernameAvailableState.value = isAvailable
                _usernameError.value = ""
            } else {
                _usernameError.value = tooShortError
            }
        }
    }

    fun changeUsername(usernameToFindUser: String, newUsername: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank()) {
                // checking if new username is available
                _isLoading.value = true
                delay(500)
                checkUsername(newUsername)
                _isLoading.value = false
                // changing username if it is available
                    if (_isUsernameAvailableState.value == true && _usernameError.value.isBlank()) {
                        authRepository.changeUsername(usernameToFindUser, newUsername)
                    }
            }
        }
    }

    // NAME FUNCTIONALITY
    private val _nameError = mutableStateOf("")
    val nameError = _nameError
    fun changeName(usernameToFindUser: String, newName: String) {
        val error = "Name must contain at least 4 characters"
        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.value = true
            delay(500)
            if (usernameToFindUser.isNotBlank() && newName.length >= 4) {
                authRepository.changeName(usernameToFindUser, newName)
                _nameError.value = ""
            } else {
                _nameError.value = error
            }
            _isLoading.value = false
        }
    }


    // EMAIL FUNCTIONALITY
    // error
    private val _emailError = mutableStateOf("")
    val emailError = _emailError

    fun changeEmail(usernameToFindUser: String, newEmail: String) {
        val error = "Email is not valid"
        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.value = true
            delay(500)
            val isValid = Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
            if (isValid) {
                authRepository.changeEmail(usernameToFindUser, newEmail)
                _emailError.value = ""
            } else {
                _emailError.value = error
            }
            _isLoading.value = false
        }
    }

    // BIO FUNCTIONALITY
    fun changeUserBio(usernameToFindUser: String, newBio: String = "Bio") {
        CoroutineScope(Dispatchers.IO).launch {
            if (usernameToFindUser.isNotBlank()) {
                authRepository.changeUserBio(usernameToFindUser, newBio)
            }
        }
    }


    // AUTHENTICATION
   private fun authenticate() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.authenticate()
            resultChannel.send(result)
        }
    }
}