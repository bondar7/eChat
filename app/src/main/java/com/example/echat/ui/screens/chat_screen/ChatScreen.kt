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
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import com.example.echat.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavHostController,
    searchUsersViewModel: SearchUsersViewModel,
    chatViewModel: ChatViewModel
) {
    val selectedUser = searchUsersViewModel.selectedUser.value
    val state = chatViewModel.state.value

    Scaffold(
        containerColor = Color(0xFFF7F7FA),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Box(modifier = Modifier.clip(CircleShape).clickable {
                                popBackStack(navController, chatViewModel)
                            }) {
                                CircularUserAvatar(
                                    avatar = selectedUser?.avatar,
                                    imageSize = 40.dp
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Column {
                                Text(
                                    text = selectedUser?.name ?: selectedUser?.username!!,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Is online now",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack(navController, chatViewModel) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = ElementColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = ElementColor
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = ElementColor
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = ElementColor
                        )
                    }
                }
            )
        },
        bottomBar = {
            SendMessage(
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
            MessagesList(
                state.messages,
                selectedUser?.id!!
            )
        }
    }
}

private fun popBackStack(navController: NavHostController, chatViewModel: ChatViewModel) {
    navController.popBackStack()
    chatViewModel.updateSelectedSessionId("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SendMessage(
    onSendMessage: (String) -> Unit
) {
    var textState by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp, top = 10.dp)
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFECECEC),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Type...",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .weight(1f)
                    .height(50.dp),
                singleLine = true,
            )
            IconButton(
                onClick = {
                    onSendMessage(textState)
                    textState = ""
                },
                modifier = Modifier.weight(0.2f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = null,
                    tint = ElementColor
                )
            }
        }
    }
}

@Composable
private fun MessagesList(
    messages: List<Message>,
    selectedUserId: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 70.dp, bottom = 75.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            val isOwnMessage = message.senderId != selectedUserId
            MessageListItem(isOwnMessage = isOwnMessage, message = message)
        }
    }
}

@Composable
private fun MessageListItem(
    isOwnMessage: Boolean,
    message: Message
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = if (isOwnMessage) {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(20.dp))) {
            Column(
                modifier = Modifier
                    .width(250.dp)
                    .background(
                        color = if (isOwnMessage) ElementColor else Color.DarkGray,
                    )
                    .padding(8.dp)
            ) {
                Text(text = message.content, color = Color.White)
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = message.formattedTime,
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}