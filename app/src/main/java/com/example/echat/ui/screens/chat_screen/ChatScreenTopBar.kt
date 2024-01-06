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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.echat.R
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 0.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(Modifier.weight(1f)) {
                CreateIconButton(
                    painter = painterResource(id = R.drawable.arrowback),
                    tint = Color.Black,
                    size = 20.dp,
                    modifier = Modifier
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp),
                    onClick = { onCloseChat() }
                )
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
                                imageSize = 60.dp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = selectedUser?.name ?: "Unknown User",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 17.5.sp,
                                    fontFamily = gliroy,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Is online now",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontFamily = gliroy,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }

            Row(Modifier.weight(0.3f)) {
                CreateIconButton(
                    painter = painterResource(id = R.drawable.phonecall),
                    tint = Color.Black,
                    size = 24.dp,
                    onClick = {}
                )
                CreateIconButton(
                    painter = painterResource(id = R.drawable.videocall),
                    tint = Color.Black,
                    size = 24.dp,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun CreateIconButton(
    painter: Painter,
    tint: Color,
    description: String? = null,
    size: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }, modifier = modifier) {
        Icon(
            painter = painter,
            contentDescription = description,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}