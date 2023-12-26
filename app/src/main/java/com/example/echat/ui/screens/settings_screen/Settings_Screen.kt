package com.example.echat.ui.screens.settings_screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.R
import com.example.echat.navigation.Screen
import com.example.echat.ui.screens.BottomNavigationBar
import com.example.echat.auth.AuthViewModel
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CommitPrefEdits")
@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val user = viewModel.user
    val prefs = LocalContext.current.getSharedPreferences("prefs", MODE_PRIVATE)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF7F7FA),
        bottomBar = {
            BottomNavigationBar(navController = navHostController)
        }
    ) {
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
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.avatar),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(65.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = user.value?.name ?: user.value?.username ?: "Guest",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 19.sp,
                                    fontFamily = gliroy
                                )
                                Text(
                                    text = "online",
                                    color = Color.DarkGray,
                                    fontSize = 12.sp,
                                    fontFamily = gliroy
                                )
                            }
                    }
                    IconButton(onClick = {
                        prefs.edit().remove("USER").apply()
                        navHostController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.SettingsScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
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
                    AccountInfoItem(
                        "@${user.value?.username}",
                        "Tap to change username",
                        onClick = {
                            navHostController.navigate(Screen.EditUsernameScreen.route)
                        })
                    AccountInfoItem(
                        user.value?.name ?: "As default your name is your username",
                        "Tap to change name",
                        onClick = {
                            navHostController.navigate(Screen.EditNameScreen.route)
                        })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        user.value?.email ?: "",
                        "Tap to change email address",
                        onClick = { navHostController.navigate(Screen.EditEmailScreen.route) })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        "Change account password",
                        "Tap to change password",
                        onClick = { navHostController.navigate(Screen.EditPasswordScreen.route) })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        user.value?.bio ?: "Bio",
                        "Add a few words about yourself",
                        onClick = { navHostController.navigate(Screen.EditUserBioScreen.route) })
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
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
                        text = "Settings", modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(
                            color = ElementColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.5.sp,
                            fontFamily = gliroy
                        )
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
}

@Composable
private fun SettingsItem(icon: ImageVector, text: String) {
    Row {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = text, fontSize = 15.sp, fontFamily = gliroy)
    }
}

@Composable
private fun AccountInfoItem(text: String, description: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)) {
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