package com.example.echat.auth

interface AuthRepository {
    suspend fun signUp(username: String, phoneNumber: String, password: String): AuthResult<Unit>
    suspend fun logIn(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}