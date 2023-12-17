package com.example.echat.auth

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

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}