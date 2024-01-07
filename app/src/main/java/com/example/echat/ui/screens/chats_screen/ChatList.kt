package com.example.echat.ui.screens.chats_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echat.server.data.model.ChatSession
import com.example.echat.server.data.model.Person
import com.example.echat.ui.circular_avatar.CircularUserAvatar
import com.example.echat.ui.theme.gliroy
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatList(chats: List<ChatSession>, onChatClick: (selectedPerson: Person) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        reverseLayout = true
    ) {
        items(items = chats) {
            ListItem(chat = it, onChatClick = { onChatClick(it) })
        }
    }
}

@Composable
private fun ListItem(
    chat: ChatSession,
    onChatClick: (selectedPerson: Person) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onChatClick(chat.user)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .clip(CircleShape)
                .size(65.dp)
        ) {
            CircularUserAvatar(avatar = chat.user.avatar, imageSize = 65.dp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = chat.user.name, style = TextStyle(
                            fontFamily = gliroy,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = formatTimestamp(chat.lastMessageSentTime),
                        color = Color.Gray,
                        fontFamily = gliroy,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = chat.lastMessage,
                    style = TextStyle(
                        color = Color.Gray,
                        fontFamily = gliroy,
                        fontSize = 15.sp
                    ),
                    maxLines = 1
                )
            }

        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}