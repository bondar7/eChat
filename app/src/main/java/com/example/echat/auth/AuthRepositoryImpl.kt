package com.example.echat.auth

import android.content.Context
import android.content.SharedPreferences
import retrofit2.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.echat.data.model.User
import com.google.gson.Gson

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun signUp(
        username: String,
        phoneNumber: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val signUpRequest = SignUpRequest(username, phoneNumber, password)
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
                phoneNumber = response.phoneNumber,
                bio = response.bio,
                token = response.bio,
            )
            saveUser(receivedUser)
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
}