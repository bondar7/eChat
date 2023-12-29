package com.example.echat.ui.screens.search_users_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.echat.ui.navigation_bar.BottomNavigationBar
import com.example.echat.ui.search_bar.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchUsersScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White,
        topBar = {},
//        bottomBar = { BottomNavigationBar(navController = navHostController) }
    ) {
        SearchBar(
            onSearch = {},
            placeholderText = "Search users by username"
        )

    }
}

@Preview
@Composable
private fun Preview() {
    SearchUsersScreen()
}