package com.example.echat.auth

import com.echat_backend.data.requests.ChangePasswordRequest
import com.echat_backend.data.requests.ChangeEmailRequest
import com.echat_backend.data.requests.ChangeUsernameRequest
import com.echat_backend.data.requests.CheckPasswordRequest
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

    @POST("check-username")
    suspend fun checkUsername(
        @Body request: String
    ): Boolean

    @POST("change-username")
    suspend fun changeUsername(
        @Body request: ChangeUsernameRequest
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