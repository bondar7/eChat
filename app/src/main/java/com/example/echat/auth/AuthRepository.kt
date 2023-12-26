package com.example.echat.auth

interface AuthRepository {
    suspend fun signUp(username: String, email: String, password: String): AuthResult<Unit>
    suspend fun logIn(username: String, password: String): AuthResult<Unit>
    suspend fun checkUsername(username: String): Boolean
    suspend fun changeUsername(usernameToFindUser: String, newUsername: String)
    suspend fun changeName(usernameToFindUser: String, newName: String)
    suspend fun changePassword(usernameToFindUser: String, newPassword: String)
    suspend fun checkPassword(usernameToFindUser: String, password: String): Boolean
    suspend fun changeUserBio(usernameToFindUser: String, newBio: String)
    suspend fun changeEmail(usernameToFindUser: String, email: String)
    suspend fun checkEmail(email: String): Boolean
    suspend fun authenticate(): AuthResult<Unit>
}