package com.example.echat.screens.settings_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
fun SettingsScreen(user: CurrentUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Image(
                    painter = user.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(65.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = user.name,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gliroy
                    )
                    Text(text = "online", color = Color.DarkGray, fontSize = 12.sp,fontFamily = gliroy)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Account", modifier = Modifier.padding(bottom = 8.dp),
                    style = TextStyle(
                        color = ElementColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.5.sp,
                        fontFamily = gliroy
                    )
                )
                AccountInfoItem("@${user.username}", "Username")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                AccountInfoItem(user.phone, "Tap to change phone number")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                AccountInfoItem(user.bio, "Add a few words about yourself")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Settings", modifier = Modifier.padding(bottom = 10.dp),
                    style = TextStyle(
                        color = ElementColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.5.sp,
                        fontFamily = gliroy
                    )
                )
                    SettingsItem(Icons.Outlined.Lock, "Privacy and Security")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    SettingsItem(Icons.Outlined.Language, "Language")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                SettingsItem(Icons.Outlined.WbSunny, "Theme")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Help", modifier = Modifier.padding(bottom = 10.dp),
                    style = TextStyle(
                        color = ElementColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.5.sp,
                        fontFamily = gliroy
                    )
                )
                SettingsItem(icon = Icons.Outlined.QuestionAnswer, text = "Ask a Question")
            }
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, text: String) {
    Row {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = text, fontSize = 15.sp, fontFamily = gliroy)
    }
}

@Composable
fun AccountInfoItem(text: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text, style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = gliroy
                )
            )
            Text(
                text = description, style = TextStyle(
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontFamily = gliroy
                )
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.Black)
        }
    }
}

data class CurrentUser(
    val avatar: Painter,
    val name: String,
    val username: String,
    val phone: String,
    val bio: String = "Bio"
)

@Preview
@Composable
fun SettingsScreenPrev() {
    Surface(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SettingsScreen(
            CurrentUser(
                painterResource(id = R.drawable.avatar),
                "Maksim Bondar",
                "b0ndar7",
                "099 375 8823"
            )
        )
    }
}