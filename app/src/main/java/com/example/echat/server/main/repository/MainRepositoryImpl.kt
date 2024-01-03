package com.example.echat.server.main.repository

import android.util.Log
import com.example.echat.server.data.model.Person
import com.example.echat.server.main.api.MainApi
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import retrofit2.HttpException

class MainRepositoryImpl(
    private val mainApi: MainApi,
): MainRepository {
    override suspend fun getUsersByUsername(username: String, searchUsersViewModel: SearchUsersViewModel): List<Person> {
       return try {
           searchUsersViewModel.setLoading(true)
           val response: List<Person> = mainApi.getUsersByUsername(username)
           searchUsersViewModel.updateUsersList(response)
           searchUsersViewModel.setLoading(false)
           response
        } catch (e: HttpException) {
            e.printStackTrace()
            emptyList<Person>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Person>()
        }
    }
}