package com.example.echat.ui.screens.authentication.auth_edit_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.auth.AuthViewModel
import com.example.echat.ui.theme.ElementColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmailScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var checkingForPassword by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Change Email") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF70ABFC),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7FA)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (checkingForPassword) {
                CheckPassword(
                    username = mainViewModel.user.value?.username!!,
                    authViewModel = authViewModel,
                    hideCheckingPassword = { checkingForPassword = false },
                    navController = navController
                )
            } else {
                EnterNewEmail(mainViewModel.user.value?.username!!, authViewModel, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterNewEmail(
    username: String,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    var textState by remember {
        mutableStateOf("")
    }
    val isLoading = authViewModel.isLoading.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "New Email",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = ElementColor,
                        strokeWidth = 2.7.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(55.dp),
                    placeholder = { Text(text = "New Email", color = Color.Gray) },
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        containerColor = Color(0xFFF7F7FA)
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            changeEmail(
                                username = username,
                                newEmail = textState,
                                authViewModel = authViewModel,
                                navController = navController,
                            )
                        }
                    )
                )
                if (authViewModel.emailError.value.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = authViewModel.emailError.value, color = Color.Red)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(text = "Cancel", fontSize = 15.sp, color = ElementColor)
                }
                TextButton(onClick = {
                    changeEmail(
                        username = username,
                        newEmail = textState,
                        authViewModel = authViewModel,
                        navController = navController,
                    )
                }) {
                    Text(text = "Confirm", fontSize = 15.sp, color = ElementColor)
                }
            }
        }
    }
}

private fun changeEmail(
    username: String,
    newEmail: String,
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
    CoroutineScope(Dispatchers.IO).launch {
        authViewModel.changeEmail(username, newEmail)
        delay(550)
        withContext(Dispatchers.Main) {
            if (authViewModel.emailError.value.isBlank()) {
                navController.popBackStack()
            }
        }
    }
}

