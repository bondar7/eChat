package com.example.echat.ui.screens.search_users_screen

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.echat.data.model.Person
import com.example.echat.server.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    private val _selectedUser: MutableState<Person?> = mutableStateOf(null)
    val selectedUser = _selectedUser


    private val _foundUsers: MutableState<List<Person>> = mutableStateOf(emptyList())
    val foundUsers = _foundUsers

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading = _isLoading

    fun updateUsersList(newUsers: List<Person>) {
        _foundUsers.value = newUsers
    }

    fun updateSelectedUser(newUser: Person) {
        _selectedUser.value = newUser
    }

    fun setLoading(newValue: Boolean) {
        _isLoading.value = newValue
    }

    fun onSearchTextStateChange(newText: String) {
     _searchTextState.value = newText
    }

    fun getUsersByUsername() {
        CoroutineScope(Dispatchers.IO).launch {
            if (_searchTextState.value.isNotBlank()) {
                mainRepository.getUsersByUsername(_searchTextState.value, this@SearchUsersViewModel)
            }
        }
    }
}