package com.example.echat.ui.screens.search_users_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import com.example.echat.server.data.model.Person
import com.example.echat.navigation.Screen
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.circular_avatar.CircularUserAvatar
import com.example.echat.ui.theme.gliroy

@Composable
fun FoundUsersList(
    foundUsers: List<Person>,
    navController: NavHostController,
    chatViewModel: ChatViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = foundUsers) {
            ListItem(
                person = it,
                onClick = {
                    chatViewModel.updateSelectedUser(it)
                    if (chatViewModel.selectedUser.value != null) {
                        navController.navigate(Screen.DetailedUserScreen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun ListItem(
    person: Person,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .size(65.dp)
        ) {
            CircularUserAvatar(avatar = person.avatar, imageSize = 65.dp)
        }
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Tap for details", style = TextStyle(
                        fontFamily = gliroy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    ),
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = person.name, style = TextStyle(
                        fontFamily = gliroy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    maxLines = 1
                )
            }
        }
    }
}