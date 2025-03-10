package com.example.echat.ui.screens.authentication.auth_edit_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditPasswordScreen(
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {

    var checkingForPassword by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Change Password") },
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
                EnterNewPassword(mainViewModel.user.value?.username!!, authViewModel, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterNewPassword(
    username: String,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    var textState by remember {
        mutableStateOf("")
    }
    var textState2 by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

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
                    text = "New password",
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
                NewPasswordField(
                    textState = textState,
                    placeholderText = "New password",
                    isPasswordVisible = isPasswordVisible,
                    onTextChange = { textState = it },
                    onDone = {
                        changePw(
                            textState = textState,
                            textState2 = textState2,
                            username = username,
                            authViewModel = authViewModel,
                            navController = navController,
                            loading = {
                                isLoading = !isLoading
                            }
                        )
                    },
                    onTrailingIcon = { isPasswordVisible = !isPasswordVisible }
                )
                Spacer(modifier = Modifier.height(10.dp))
                NewPasswordField(
                    textState = textState2,
                    placeholderText = "Repeat password",
                    isPasswordVisible = isPasswordVisible,
                    onTextChange = { textState2 = it },
                    onDone = {
                        changePw(
                            textState = textState,
                            textState2 = textState2,
                            username = username,
                            authViewModel = authViewModel,
                            navController = navController,
                            loading = {
                                isLoading = !isLoading
                            }
                        )
                    },
                    onTrailingIcon = {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
                if (authViewModel.pwError2.value.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = authViewModel.pwError2.value, color = Color.Red)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(text = "Cancel", fontSize = 15.sp, color = ElementColor)
                }
                TextButton(onClick = {
                    changePw(
                        textState = textState,
                        textState2 = textState2,
                        username = username,
                        authViewModel = authViewModel,
                        navController = navController,
                        loading = {
                            isLoading = !isLoading
                        }
                    )
                }) {
                    Text(text = "Confirm", fontSize = 15.sp, color = ElementColor)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckPassword(
    username: String,
    authViewModel: AuthViewModel,
    hideCheckingPassword: () -> Unit,
    navController: NavHostController
) {

    var textState by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    val isPasswordCorrect by authViewModel.isPasswordCorrectLiveData.observeAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Enter your password",
                    fontSize = 18.sp,
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
            Spacer(modifier = Modifier.height(20.dp))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                NewPasswordField(
                    textState = textState,
                    placeholderText = "Your current password",
                    isPasswordVisible = isPasswordVisible,
                    onTextChange = { textState = it },
                    onDone = {
                        checkPw(
                            username,
                            textState,
                            isPasswordCorrect,
                            authViewModel,
                            loading = { isLoading = !isLoading },
                            whenPasswordCorrect = {
                                if (isPasswordCorrect) hideCheckingPassword()
                            }
                        )
                    },
                    onTrailingIcon = { isPasswordVisible = !isPasswordVisible }
                )
                if (authViewModel.pwError1.value.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = authViewModel.pwError1.value, color = Color.Red)
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Cancel", fontSize = 15.sp, color = ElementColor)
                }
                TextButton(onClick = {
                    checkPw(
                        username,
                        textState,
                        isPasswordCorrect,
                        authViewModel,
                        loading = { isLoading = !isLoading },
                        whenPasswordCorrect = {
                            if (isPasswordCorrect) {
                                hideCheckingPassword()
                            }
                        }
                    )
                }
                ) {
                    Text(text = "Continue", fontSize = 15.sp, color = ElementColor)
                }
            }
        }
    }
}

private fun checkPw(
    username: String,
    password: String,
    isPwCorrect: Boolean,
    authViewModel: AuthViewModel,
    loading: () -> Unit,
    whenPasswordCorrect: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        loading()
        authViewModel.checkPassword(username, password)
        delay(1000)
        loading()
        if (password.isBlank()) {
            authViewModel.updatePwError1("Field cannot be empty")
        }

        if (!isPwCorrect && password.isNotBlank()) {
            authViewModel.updatePwError1("Incorrect password.Try again.")
        }

        whenPasswordCorrect()
    }
}

private fun changePw(
    textState: String,
    textState2: String,
    username: String,
    authViewModel: AuthViewModel,
    navController: NavHostController,
    loading: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        loading()
        delay(1000)
        loading()
        if (textState2.length <= 8 && textState.length <= 8) {
            authViewModel.updatePwError2("The password must contain at least 9 characters")
        } else if (textState2 != textState) {
            authViewModel.updatePwError2("Passwords do not match")
        } else {
            authViewModel.changePassword(username, textState2)
            withContext(Dispatchers.Main) {
                navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewPasswordField(
    textState: String,
    placeholderText: String,
    isPasswordVisible: Boolean,
    onTextChange: (newText: String) -> Unit,
    onDone: () -> Unit,
    onTrailingIcon: () -> Unit
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(55.dp),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text(text = placeholderText, color = Color.DarkGray, fontSize = 16.sp) },
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = Color(0xFFF7F7FA)
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        trailingIcon = {
            IconButton(onClick = { onTrailingIcon() }) {
                Icon(
                    imageVector =
                    if (isPasswordVisible) Icons.Default.VisibilityOff
                    else Icons.Default.Visibility,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }
        },
    )
}