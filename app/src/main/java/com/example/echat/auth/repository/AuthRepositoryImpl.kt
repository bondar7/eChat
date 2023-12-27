package com.example.echat.auth.repository

import android.content.SharedPreferences
import retrofit2.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.echat.auth.requests.ChangePasswordRequest
import com.example.echat.auth.requests.ChangeEmailRequest
import com.example.echat.auth.requests.ChangeNameRequest
import com.example.echat.auth.requests.ChangeUsernameRequest
import com.example.echat.auth.requests.CheckPasswordRequest
import com.example.echat.MainViewModel
import com.example.echat.auth.AuthResult
import com.example.echat.auth.api.AuthApi
import com.example.echat.auth.requests.ChangeUserBioRequest
import com.example.echat.auth.requests.LogInRequest
import com.example.echat.auth.requests.SignUpRequest
import com.example.echat.data.model.User
import com.google.gson.Gson

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val prefs: SharedPreferences,
    private val mainViewModel: MainViewModel
) : AuthRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun signUp(
        username: String,
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val signUpRequest = SignUpRequest(username, email, password)
            authApi
                .signUp(signUpRequest)
            logIn(username, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun logIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val logInRequest = LogInRequest(username, password)
            val response = authApi.logIn(logInRequest)
            val receivedUser = User(
                username = response.username,
                email = response.email,
                bio = response.bio,
                token = response.token,
            )
            saveUser(receivedUser)
            mainViewModel.updateUser(receivedUser)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun changeUsername(usernameToFindUser: String, newUsername: String) {
        try {
            val request = ChangeUsernameRequest(
                usernameToFindUser = usernameToFindUser,
                newUsername = newUsername
            )
            authApi.changeUsername(request)
            val user = getUser()
            if (user != null) {
                val updatedUser = user.copy(username = newUsername)
                removeUser()
                saveUser(updatedUser)
                mainViewModel.updateUser(updatedUser)
            }
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override suspend fun changeName(usernameToFindUser: String, newName: String) {
        try {
            val request = ChangeNameRequest(
                usernameToFindUser = usernameToFindUser,
                newName = newName
            )
            authApi.changeName(request)
            val user = getUser()
            if (user != null) {
                val updatedUser = user.copy(name = newName)
                removeUser()
                saveUser(updatedUser)
                mainViewModel.updateUser(updatedUser)
            }
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) { 
            e.printStackTrace()
        }
    }

    override suspend fun checkUsername(username: String): Boolean {
        return try {
            authApi.checkUsername(username)
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun changePassword(usernameToFindUser: String, newPassword: String) {
        try {
            val request = ChangePasswordRequest(
                usernameToFindUser = usernameToFindUser,
                newPassword = newPassword
            )
            authApi.changePassword(request)
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun checkPassword(usernameToFindUser: String, password: String): Boolean {
        return try {
            val request = CheckPasswordRequest(usernameToFindUser, password)
            authApi.checkPassword(request)
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun changeEmail(usernameToFindUser: String, newEmail: String) {
        try {
            val request = ChangeEmailRequest(
                usernameToFindUser = usernameToFindUser,
                newEmail = newEmail
            )
            authApi.changeEmail(request)
            val user = getUser()
            if (user != null) {
                val updatedUser = user.copy(email = newEmail)
                removeUser()
                saveUser(updatedUser)
                mainViewModel.updateUser(updatedUser)
            }
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun checkEmail(email: String): Boolean {
        return try {
            authApi.checkEmail(email)
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun changeUserBio(usernameToFindUser: String, newBio: String) {
        try {
            val request = ChangeUserBioRequest(
                usernameToFindUser = usernameToFindUser,
                newBio = newBio
            )
            authApi.changeUserBio(request)
            val user = getUser()
            if (user != null) {
                val updatedUser = user.copy(bio = newBio)
                removeUser()
                saveUser(updatedUser)
                mainViewModel.updateUser(updatedUser)
            }
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val user = getUser() ?: return AuthResult.Unauthorized()
            authApi.authenticate("Bearer ${user.token}")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    private val gson = Gson()

    fun saveUser(user: User) {
        val editor = prefs.edit()
        val userJson = gson.toJson(user)
        editor.putString("USER", userJson)
        editor.apply()
    }

    fun getUser(): User? {
        val userJson = prefs.getString("USER", null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun removeUser() {
        prefs.edit().remove("USER").apply()
    }
}