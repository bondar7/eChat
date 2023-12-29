package com.example.echat.server.main.api

import com.example.echat.data.model.Person
import com.example.echat.server.auth.requests.ChangePasswordRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {
    @GET("get-users-by-username")
    suspend fun getUsersByUsername(
        @Query("username") username: String
    ): List<Person>
}