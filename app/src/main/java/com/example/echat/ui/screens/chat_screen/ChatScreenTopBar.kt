package com.example.echat.ui.screens.chat_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.echat.navigation.Screen
import com.example.echat.server.data.model.Person
import com.example.echat.ui.circular_avatar.CircularUserAvatar
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenTopBar(
    selectedUser: Person?,
    navHostController: NavHostController,
    onCloseChat: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            navHostController.navigate(Screen.DetailedUserScreen.route)
                        }) {
                        CircularUserAvatar(
                            avatar = selectedUser?.avatar,
                            imageSize = 40.dp
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(
                            text = selectedUser?.name ?: "Unknown User",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 15.sp,
                                fontFamily = gliroy,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "Is online now",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontFamily = gliroy,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {  onCloseChat() }) {
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
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White
        )
    )
}