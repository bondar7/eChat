package com.example.echat.ui.screens.chat_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.server.data.model.Message
import com.example.echat.navigation.Screen
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.CircularUserAvatar
import com.example.echat.ui.screens.detailed_user_screen.DetailedUserScreen
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import com.example.echat.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavHostController,
    chatViewModel: ChatViewModel,

) {
    val selectedUser = chatViewModel.selectedUser.value
    val state = chatViewModel.state.value

    Scaffold(
        containerColor = Color(0xFFF7F7FA),
        topBar = {
            ChatScreenTopBar(
                navController = navController,
                selectedUser = selectedUser,
            )
        },
        bottomBar = {
            ChatScreenSendMessage(
                onSendMessage = {
                    if (it.isNotBlank()) {
                        chatViewModel.sendMessage(it)
                    }
                }
            )
        }
    ) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(45.dp),
                    color = ElementColor,
                    strokeWidth = 4.5.dp
                )
            }
        } else {
            if (selectedUser?.id != null) {
                ChatScreenMessagesList(
                    state.messages,
                    selectedUser.id
                )
            }
        }
    }
}