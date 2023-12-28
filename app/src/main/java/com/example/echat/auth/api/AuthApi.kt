package com.example.echat.auth.api

import com.echat_backend.data.requests.ChangeAvatarRequest
import com.example.echat.auth.responses.AuthResponse
import com.example.echat.auth.requests.ChangePasswordRequest
import com.example.echat.auth.requests.ChangeEmailRequest
import com.example.echat.auth.requests.ChangeNameRequest
import com.example.echat.auth.requests.ChangeUsernameRequest
import com.example.echat.auth.requests.CheckPasswordRequest
import com.example.echat.auth.requests.ChangeUserBioRequest
import com.example.echat.auth.requests.LogInRequest
import com.example.echat.auth.requests.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    )

    @POST("login")
    suspend fun logIn(
        @Body request: LogInRequest
    ): AuthResponse

    @POST("change-avatar")
    suspend fun changeAvatar(
        @Body request: ChangeAvatarRequest
    )


    @POST("check-username")
    suspend fun checkUsername(
        @Body request: String
    ): Boolean

    @POST("change-username")
    suspend fun changeUsername(
        @Body request: ChangeUsernameRequest
    )

    @POST("change-name")
    suspend fun changeName(
        @Body request: ChangeNameRequest
    )

    @POST("change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    )
    @POST("check-password")
    suspend fun checkPassword(
        @Body request: CheckPasswordRequest
    ): Boolean

    @POST("change-email")
    suspend fun changeEmail(
        @Body request: ChangeEmailRequest
    )
    @POST("check-email")
    suspend fun checkEmail(
        @Body request: String
    ): Boolean

    @POST("change-user-bio")
    suspend fun changeUserBio(
        @Body request: ChangeUserBioRequest
    )


    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}