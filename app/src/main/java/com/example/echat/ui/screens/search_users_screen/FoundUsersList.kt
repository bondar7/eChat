package com.example.echat.ui.screens.search_users_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.echat.server.data.model.Person
import com.example.echat.navigation.Screen
import com.example.echat.ui.CircularUserAvatar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FoundUsersList(
    foundUsers: List<Person>,
    navController: NavHostController,
    searchUsersViewModel: SearchUsersViewModel,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7FA)),
    ) {
        items(items = foundUsers) {
            Person(
                person = it,
                onClick = {
                        searchUsersViewModel.updateSelectedUser(it)
                        if (searchUsersViewModel.selectedUser.value != null) {
                            navController.navigate(Screen.DetailedUserScreen.route)
                        }
                }
            )
        }
    }
}

@Composable
private fun Person(
    person: Person,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularUserAvatar(avatar = person.avatar, imageSize = 55.dp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = person.name, color = Color.Black)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                Text(
                    text = "Tap for details",
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 10.dp, top = 5.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(
                    Color.Gray
                )
        )
    }
}