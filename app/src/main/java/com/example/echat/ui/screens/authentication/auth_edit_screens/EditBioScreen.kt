package com.example.echat.ui.screens.authentication.auth_edit_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.ui.theme.ElementColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBioScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel
) {

    val topBarTitle = "Bio"
    val initialValue = mainViewModel.user.value?.bio ?: ""

    var textState by remember {
        mutableStateOf(initialValue)
    }

    Scaffold(
        containerColor = Color.White,
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
                    IconButton(onClick = { onConfirm(authViewModel, mainViewModel, navController, textState) }) {
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
        val focusRequester = remember { FocusRequester() }

        // Use the onGloballyPositioned modifier to request focus when the composable is laid out
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 85.dp, start = 15.dp, end = 15.dp)
                .onGloballyPositioned {
                    focusRequester.requestFocus()
                }
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester) // Assign the FocusRequester
                    .onGloballyPositioned {
                        focusRequester.requestFocus()
                    },
                value = textState,
                onValueChange = { textState = it },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 17.sp
                ),

            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.8.dp)
                    .background(ElementColor)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "You can add a few lines about yourself.", color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Max length is 80 characters", color = Color.Gray)
        }
    }
}

private fun onConfirm(
    authViewModel: AuthViewModel,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    newBio: String
) {
    val user = mainViewModel.user.value
    if (user!= null) {
        authViewModel.changeUserBio(user.username, newBio)
        mainViewModel.user.value = user.copy(bio = newBio)
        navController.popBackStack()
    }
}

private fun onRefuse(
    navController: NavHostController,
) {
    navController.popBackStack()
}