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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNameScreen(
    topBarTitle: String,
    blueTitle: String,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel
) {

    var textState by remember {
        mutableStateOf(mainViewModel.user.value?.name ?: "")
    }
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
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                        )
                        Icon(
                            imageVector =
                            if (authViewModel.nameError.value.isBlank()) Icons.Default.Done
                            else Icons.Default.Close,
                            contentDescription = null,
                            tint =
                            if (authViewModel.nameError.value.isBlank()) Color.Green
                            else Color.Red,
                            modifier = Modifier.weight(0.1f)
                        )
                    }
                }
            }
            if (authViewModel.nameError.value.isNotBlank()) {
                Text(text = authViewModel.nameError.value, color = Color.Red)
            }
        }
    }
}

private fun onConfirm(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    newName: String,
) {
    val user = mainViewModel.user.value
    if (user != null) {
        CoroutineScope(Dispatchers.IO).launch {
            authViewModel.changeName(
                usernameToFindUser = user.username,
                newName = newName
            )
            delay(550)
            if (authViewModel.nameError.value.isBlank()) {
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
                }
            }
        }
    }
}

private fun onRefuse(
    navController: NavHostController,
) {
    navController.popBackStack()
}