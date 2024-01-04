package com.example.echat.ui.screens.chats_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.navigation.Screen
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.navigation_bar.BottomNavigationBar
import com.example.echat.ui.search_bar.SearchBar
import com.example.echat.ui.theme.MainBackgroundColor
import com.example.echat.ui.theme.gliroy
import com.example.echat.utils.observeAuthResultsAndNavigate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    viewModel: MainViewModel,
    authViewModel: AuthViewModel,
    chatsViewModel: ChatsViewModel,
    chatViewModel: ChatViewModel,
    navHostController: NavHostController
) {
    val chatSessions = chatsViewModel.chatSessions.value
    val context = LocalContext.current

    DisposableEffect(key1 = Unit) {
        chatsViewModel.getSessionsByUserId()
        onDispose {  }
    }

    LaunchedEffect(key1 = viewModel, key2 = context) {
        observeAuthResultsAndNavigate(
            authViewModel,
            navHostController,
            context
        )
    }

    Scaffold(
        containerColor = MainBackgroundColor,
        bottomBar = { BottomNavigationBar(navController = navHostController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            // Top content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hello ${viewModel.user.value?.name} 👋", style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        fontFamily = gliroy
                    )
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            // Search Bar
            SearchBar(
                textState = chatsViewModel.searchTextState.value,
                onTextChange = { chatsViewModel.onSearchTextStateChange(it) },
                onSearch = {}
            )

            Spacer(modifier = Modifier.height(25.dp))
            // Chat List
            ChatList(
                chats = chatSessions,
                onChatClick = { selectedPerson ->
                    chatViewModel.updateSelectedUser(selectedPerson)
                    val currentUserId = viewModel.user.value?.id
                    val userId = selectedPerson.id
                    if (currentUserId != null) {
                        chatViewModel.startChat(
                            user1Id = userId,
                            currentUserId = currentUserId,
                            navController = navHostController
                        )
                    }
                }
            )
        }
    }
}
