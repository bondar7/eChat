package com.example.echat.ui.screens.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echat.server.data.model.Message
import com.example.echat.ui.theme.ElementColor

@Composable
fun ChatScreenMessagesList(
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