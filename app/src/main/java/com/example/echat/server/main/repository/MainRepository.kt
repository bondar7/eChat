package com.example.echat.server.main.repository

import com.example.echat.server.data.model.Person
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel

interface MainRepository {
    suspend fun getUsersByUsername(username: String, searchUsersViewModel: SearchUsersViewModel): List<Person>
}