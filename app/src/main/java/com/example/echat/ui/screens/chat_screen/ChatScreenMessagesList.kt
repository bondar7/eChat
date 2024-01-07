package com.example.echat.ui.screens.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.echat.server.data.model.Message
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@Composable
fun ChatScreenMessagesList(
    messages: List<Message>,
    selectedUserId: String,
    onImageMessageClick: (selectedImage: ByteArray) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 100.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            val isOwnMessage = message.senderId != selectedUserId
            MessageListItem(
                isOwnMessage = isOwnMessage,
                message = message,
                onImageClick = { onImageMessageClick(it) })
        }
    }
}

@Composable
private fun MessageListItem(
    isOwnMessage: Boolean,
    message: Message,
    onImageClick: (selectedImage: ByteArray) -> Unit
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
        Column() {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = if(isOwnMessage) 18.dp else 0.dp,
                            bottomStart = 18.dp,
                            bottomEnd = 18.dp,
                            topEnd = if(isOwnMessage) 0.dp else 18.dp
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .widthIn(min = 50.dp, max = 280.dp)
                        .background(
                            color = if (isOwnMessage) ElementColor else Color.White,
                        )
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (message.text != null) {
                        Text(
                            text = message.text,
                            style = TextStyle(
                                color = if (isOwnMessage) Color.White else Color.Black,
                                fontFamily = gliroy,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    if (message.image != null) {
                        AsyncImage(
                            model = message.image,
                            contentDescription = null,
                            modifier = Modifier
                                .width(250.dp)
                                .heightIn(min = 100.dp, max = 350.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    onImageClick(message.image)
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message.formattedTime,
                style = TextStyle(
                    color = Color.Gray,
                    fontFamily = gliroy,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                ),
                modifier = Modifier
                    .align(if (isOwnMessage) Alignment.End else Alignment.Start)
                    .padding(end = 10.dp, start = 10.dp)
            )
        }
    }
}