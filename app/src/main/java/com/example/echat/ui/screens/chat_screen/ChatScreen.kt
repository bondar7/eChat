package com.example.echat.ui.screens.chat_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.photo_picker.PhotoPicker
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
    var showPhotoPicker by remember { mutableStateOf(false) }
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
                },
                onPickerShow = {
                    showPhotoPicker = !showPhotoPicker
                }
            )
        }
    ) {
        if (showPhotoPicker) {
            PhotoPicker(onResult = {

            })
        }

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