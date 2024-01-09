package com.example.echat.ui.screens.chat_screen

import android.media.MediaPlayer
import android.util.Base64
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.echat.server.data.model.Message
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes

@Composable
fun ChatScreenMessagesList(
    messages: List<Message>,
    selectedUserId: String,
    onImageMessageClick: (selectedImage: ByteArray) -> Unit,
    playAudio: (audioData: ByteArray) -> Unit,
    stopAudio: () -> Unit,
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
                onImageClick = { onImageMessageClick(it) },
                onPlayAudio = { playAudio(it) },
                onStopAudio = { stopAudio() }
            )
        }
    }
}

@Composable
private fun MessageListItem(
    isOwnMessage: Boolean,
    message: Message,
    onImageClick: (selectedImage: ByteArray) -> Unit,
    onPlayAudio: (audioData: ByteArray) -> Unit,
    onStopAudio: () -> Unit
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
                            topStart = if (isOwnMessage) 18.dp else 0.dp,
                            bottomStart = 18.dp,
                            bottomEnd = 18.dp,
                            topEnd = if (isOwnMessage) 0.dp else 18.dp
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
                    when {
                        message.text != null -> {
                            TextMessage(text = message.text, isOwnMessage = isOwnMessage)
                        }
                        message.image != null -> {
                            ImageMessage(
                                image = message.image, onClick = { onImageClick(message.image) }
                            )
                        }
                        message.audio != null -> {
                            AudioMessage(
                                onPlay = {
                                    onPlayAudio(message.audio)
                                },
                                onStop = {
                                    onStopAudio()
                                },
                                audioData = message.audio
                            )
                        }
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


@Composable
private fun AudioMessage(onPlay: () -> Unit, onStop: () -> Unit, audioData: ByteArray) {
    var isPlaying by remember { mutableStateOf(false) }
    var durationInSeconds by remember { mutableStateOf(0L) }

    DisposableEffect(audioData) {
        val mediaPlayer = MediaPlayer()

        // Set audio data to the media player
        mediaPlayer.setDataSource("data:audio/mp3;base64," + Base64.encodeToString(audioData, Base64.DEFAULT))

        // Prepare the media player to get the duration
        mediaPlayer.prepare()

        // Get the duration in seconds
        durationInSeconds = mediaPlayer.duration / 1000L

        // Release the media player
        mediaPlayer.release()

        onDispose {
            // Clean up resources when the composable is disposed
            mediaPlayer.release()
        }
    }

    Row {
        Column {
            IconButton(
                onClick = {
                    if (isPlaying) onStop() else onPlay()
                    isPlaying = !isPlaying
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = ElementColor
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = formatDuration(durationInSeconds),
                style = MaterialTheme.typography.body2.copy(color = LocalTextStyle.current.color),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
private fun formatDuration(durationInSeconds: Long): String {
    val minutes = durationInSeconds / 60
    val seconds = durationInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Composable
private fun TextMessage(text: String, isOwnMessage: Boolean) {
    Text(
        text = text,
        style = TextStyle(
            color = if (isOwnMessage) Color.White else Color.Black,
            fontFamily = gliroy,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
private fun ImageMessage(image: ByteArray, onClick: () -> Unit) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier
            .width(250.dp)
            .heightIn(min = 100.dp, max = 350.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
    )
}