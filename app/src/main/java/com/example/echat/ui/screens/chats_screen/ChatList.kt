package com.example.echat.ui.screens.chats_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echat.R
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@Composable
fun ChatList(chats: List<ChatModel>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
        items(items = chats) {
            ListItem(chat = it)
        }
    }
}

data class ChatModel(
    val image: ImageVector,
    val username: String,
    val lastMessage: String,
    val isUserOnline: Boolean,
    val isUserTyping: Boolean,
    val lastMessageTime: String,
    val unreadMessages: Int = 0,
)

@Composable
fun ListItem(chat: ChatModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .clip(CircleShape)
                .size(65.dp)
        ) {
            Image(
                imageVector = chat.image,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(65.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = chat.username, style = TextStyle(
                            fontFamily = gliroy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .size(10.dp)
                        .background(if (chat.isUserOnline) Color.Green else Color.Gray))
                }
                Spacer(modifier = Modifier.height(5.dp))
                if (chat.isUserTyping) {
                    Text(
                        text = "${chat.username} is typing...", style = TextStyle(
                            color = ElementColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                } else {
                    Text(
                        text = chat.lastMessage, style = TextStyle(
                            color = Color.Gray,
                            fontFamily = gliroy,
                            fontSize = 15.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            if (chat.unreadMessages == 0) {
                Text(text = chat.lastMessageTime, color = Color.Gray, fontSize = 13.sp)
            } else {
                Box(
                    modifier = Modifier
                        .background(ElementColor)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = chat.unreadMessages.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(chat = ChatModel(
        image = Icons.Default.Person,
        username = "Maksim Bondar",
        lastMessage = "I love them! Lets go.",
        isUserOnline = false,
        isUserTyping = false,
        lastMessageTime = "08:34",
        unreadMessages = 0
    )
    )
}