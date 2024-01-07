package com.example.echat.ui.screens.settings_screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.echat.MainViewModel
import com.example.echat.navigation.Screen
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.ui.circular_avatar.CircularUserAvatar
import com.example.echat.ui.navigation_bar.BottomNavigationBar
import com.example.echat.ui.photo_picker.PhotoPicker
import com.example.echat.ui.photo_picker.uriToByteArray
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CommitPrefEdits")
@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel,
    authViewModel: AuthViewModel,
) {
    val user = viewModel.user.value
    val context = LocalContext.current
    val prefs = LocalContext.current.getSharedPreferences("prefs", MODE_PRIVATE)

    var showPhotoPicker by remember {
        mutableStateOf(false)
    }
    var showFullPhoto by remember {
        mutableStateOf(false)
    }

    val isAvatarLoading = viewModel.isAvatarLoading.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF7F7FA),
        bottomBar = {
            BottomNavigationBar(navController = navHostController)
        }
    ) {

        // photo picker
        if (showPhotoPicker) {
            PhotoPicker(
                onResult = {uri ->
                    if (uri != null && user != null) {
                        CoroutineScope(Dispatchers.Default).launch {
                            val byteArray = uriToByteArray(context, uri)
                            if (byteArray != null) {
                                authViewModel.changeAvatar(user.username, byteArray)
                            }
                        }
                    }
                }
            )
        }

        if (showFullPhoto && user?.avatar != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(10f)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = { showFullPhoto = false },
                            modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Image(
                        painter = rememberAsyncImagePainter(user.avatar),
                        contentDescription = null
                    )
                }
            }
        }

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
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(70.dp)
                                .combinedClickable(
                                    onClick = {
                                        showPhotoPicker = !showPhotoPicker
                                        if (showFullPhoto) {
                                            showFullPhoto = false
                                        }
                                    },
                                    onLongClick = { showFullPhoto = !showFullPhoto }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isAvatarLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(25.dp),
                                    color = ElementColor,
                                    strokeWidth = 3.1.dp
                                )
                            } else {
                                CircularUserAvatar(
                                    user?.avatar,
                                    70.dp
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)) // Затемнення
                                )

                                // Кнопка "Edit"
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = user?.name ?: user?.username ?: "Guest",
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                fontFamily = gliroy
                            )
                            Text(
                                text = "online",
                                color = Color.DarkGray,
                                fontSize = 13.sp,
                                fontFamily = gliroy
                            )
                        }
                    }
                    IconButton(onClick = {
                        authViewModel.logout(prefs, navHostController)
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.Logout, contentDescription = null)
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
                        "@${user?.username}",
                        "Tap to change username",
                        onClick = {
                            navHostController.navigate(Screen.EditUsernameScreen.route)
                        })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        user?.name ?: "As default your name is your username",
                        "Tap to change name",
                        onClick = {
                            navHostController.navigate(Screen.EditNameScreen.route)
                        })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        user?.email ?: "",
                        "Tap to change email address",
                        onClick = { navHostController.navigate(Screen.EditEmailScreen.route) })
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
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
                            .padding(vertical = 3.dp)
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    AccountInfoItem(
                        user?.bio ?: "Bio",
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
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
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
    }
}
