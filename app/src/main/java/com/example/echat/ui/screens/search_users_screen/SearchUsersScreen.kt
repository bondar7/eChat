package com.example.echat.ui.screens.search_users_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.R
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.ui_utils.navigation_bar.BottomNavigationBar
import com.example.echat.ui.ui_utils.search_bar.SearchBar
import com.example.echat.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchUsersScreen(
    navController: NavHostController,
    searchUsersViewModel: SearchUsersViewModel,
    chatViewModel: ChatViewModel
) {
    val foundUsers = searchUsersViewModel.foundUsers
    val isLoading = searchUsersViewModel.isLoading

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White,
        topBar = {},
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                SearchBar(
                    textState = searchUsersViewModel.searchTextState.value,
                    onTextChange = { searchUsersViewModel.onSearchTextStateChange(it) },
                    onSearch = {
                        searchUsersViewModel.getUsersByUsername()
                    },
                    placeholderText = "Search users by username"
                )
            }
            if (!isLoading.value) {
                FoundUsersList(
                    foundUsers = foundUsers.value,
                    navController,
                    chatViewModel
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(45.dp),
                        color = ElementColor,
                        strokeWidth = 4.5.dp
                    )
                }
            }
            if (foundUsers.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.no_user_found),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(text = "No user found", fontSize = 22.sp)
                    }
                }
            }
        }
    }
}