package com.example.echat.ui.screens.chat_screen

import android.annotation.SuppressLint
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.multiple_photo_picker.MultiplePhotoPicker
import com.example.echat.ui.photo_picker.PhotoPicker
import com.example.echat.ui.photo_picker.uriToByteArray
import com.example.echat.ui.theme.ElementColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavHostController,
    chatViewModel: ChatViewModel,
) {
    val selectedUser = chatViewModel.selectedUser.value
    val state = chatViewModel.state.value
    val context = LocalContext.current
    var showPhotoPicker: Boolean by remember { mutableStateOf(false) }
    var showFullPhoto: Boolean by remember { mutableStateOf(false) }
    var selectedPhoto: ByteArray? by remember { mutableStateOf(null) }


    DisposableEffect(navController) {
        onDispose {
            chatViewModel.closeChat()
        }
    }

    if (showFullPhoto && selectedPhoto != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f)
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = { showFullPhoto = false },
                        modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                AsyncImage(
                    model = selectedPhoto,
                    contentDescription = null
                )
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF7F7FA),
        topBar = {
            ChatScreenTopBar(
                selectedUser = selectedUser,
                navController,
                onCloseChat = {
                    navController.popBackStack()
                }
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
            MultiplePhotoPicker(
                onResult = { uris ->
                    CoroutineScope(Dispatchers.IO).launch {
                        uris.forEach { uri ->
                            if (uri != null) {
                                val byteArray = uriToByteArray(context, uri)
                                if (byteArray != null) {
                                    chatViewModel.sendImageMessage(byteArray)
                                }
                            }
                        }
                    }
                }
            )
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
                    selectedUser.id,
                    onImageMessageClick = {
                        showFullPhoto = !showFullPhoto
                        selectedPhoto = it
                    }
                )
            }
        }
    }
}