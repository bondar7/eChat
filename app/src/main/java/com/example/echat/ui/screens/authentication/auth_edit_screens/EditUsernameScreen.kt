package com.example.echat.ui.screens.authentication.auth_edit_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUsernameScreen(
    topBarTitle: String,
    blueTitle: String,
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var textState by remember {
        mutableStateOf(mainViewModel.user.value?.username ?: "")
    }
    val isUsernameAvailable = authViewModel.isUsernameAvailable.value
    val isLoading = authViewModel.isLoading.value

    Scaffold(
        containerColor = Color(0xFFECECEC),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        fontWeight = FontWeight.Medium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onRefuse(navController) }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onConfirm(
                            authViewModel,
                            mainViewModel,
                            navController,
                            textState,
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF70ABFC),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Column(Modifier.padding(top = 64.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(Modifier.background(Color.White)) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = blueTitle,
                            color = ElementColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.5.dp),
                                color = ElementColor,
                                strokeWidth = 2.5.dp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        BasicTextField(
                            value = textState,
                            onValueChange = {
                                textState = it
                                if (authViewModel.isUsernameAvailable.value != null) {
                                    authViewModel.setUsernameAvailableAsNull()
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                        )
                        if (isUsernameAvailable != null) {
                            Icon(
                                imageVector =
                                if (isUsernameAvailable) Icons.Default.Done
                                else Icons.Default.Close,
                                contentDescription = null,
                                tint =
                                if (isUsernameAvailable) Color.Green
                                else Color.Red,
                                modifier = Modifier.weight(0.1f).size(20.dp)
                            )
                        }
                    }
                }
            }

            if (authViewModel.usernameError.value.isBlank()) {
                if (isUsernameAvailable != null) {
                    if (!isUsernameAvailable) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Username is not available", color = Color.Red)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = authViewModel.usernameError.value, color = Color.Red)
            }

            Column(
                modifier = Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Your username is used to find you by this username and contact you without needing your phone number.",
                    style = TextStyle(
                        fontFamily = gliroy,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                )
                Text(
                    text = "Minimum length is 4 characters.",
                    style = TextStyle(
                        fontFamily = gliroy,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                )
            }

        }
    }
}

private fun onConfirm(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    newUsername: String,
) {
    val user = mainViewModel.user.value
    if (user != null) {
        authViewModel.changeUsername(
            usernameToFindUser = user.username,
            newUsername = newUsername
        )
        if (authViewModel.isUsernameAvailable.value == true) {
            navController.popBackStack()
        }
    }
}

private fun onRefuse(
    navController: NavHostController,
) {
    navController.popBackStack()
}