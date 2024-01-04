package com.example.echat.ui.screens.detailed_user_screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.echat.MainViewModel
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.circular_avatar.CircularUserAvatar
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import com.example.echat.ui.theme.ElementColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedUserScreen(
    searchUsersViewModel: SearchUsersViewModel,
    navController: NavHostController,
    chatViewModel: ChatViewModel,
    mainViewModel: MainViewModel
) {
    val user = chatViewModel.selectedUser.value

    var showFullPhoto by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        startChat(
                            searchUsersViewModel,
                            mainViewModel,
                            chatViewModel,
                            navController
                        )
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Message,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFF7F7FA)
                )
            )
        }
    ) {
        if (showFullPhoto && user?.avatar != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(10f)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Column() {
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
                    Image(painter = rememberAsyncImagePainter(user.avatar), contentDescription = null)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 64.dp)
                .background(Color(0xFFF7F7FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(Modifier.padding(10.dp)) {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .clickable { showFullPhoto = !showFullPhoto }) {
                        CircularUserAvatar(avatar = user?.avatar, imageSize = 70.dp)
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = user?.name ?: user?.username ?: "",
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "online or last seen at some time",
                            color = Color.DarkGray,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Information",
                    color = ElementColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )

                if (user?.bio?.isNotBlank() != null && user.bio != "Bio") {
                    InfoColumn(text = user.bio, subText = "Bio")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray)
                            .height(0.8.dp)
                    )
                }
                InfoColumn(
                    user?.username ?: "",
                    "Username"
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(0.8.dp)
                )
                InfoColumn(
                    "Email is hidden",
                    "Email"
                )
            }
            StartChatIcon(
                onClick = {
                    startChat(
                        searchUsersViewModel,
                        mainViewModel,
                        chatViewModel,
                        navController
                    )
                }
            )
        }
    }
}

private fun startChat(
    searchUsersViewModel: SearchUsersViewModel,
    mainViewModel: MainViewModel,
    chatViewModel: ChatViewModel,
    navController: NavHostController
) {
    val user1Id = chatViewModel.selectedUser.value?.id
    val currentUserId = mainViewModel.user.value?.id
    if (user1Id != null && currentUserId != null) {
        chatViewModel.startChat(
            user1Id = user1Id,
            currentUserId = currentUserId,
            navController = navController
        )
    }
}

@Composable
private fun InfoColumn(
    text: String,
    subText: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            Modifier.padding(15.dp)
        ) {
            Text(
                text =
                if (subText == "Username") "@${text}"
                else text,
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(
                text = subText,
                color = Color.Gray,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
private fun StartChatIcon(
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .clip(CircleShape)
                .size(65.dp)
                .background(ElementColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Message,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}