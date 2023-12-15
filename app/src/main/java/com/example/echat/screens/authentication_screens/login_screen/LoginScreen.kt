package com.example.echat.screens.authentication_screens.login_screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.screens.Screen
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(bottomEnd = 200.dp))
                    .background(Color(0xFF8DBCFC))
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 200.dp))
                    .background(Color(0xFF93BFFA))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login", style = TextStyle(
                    fontFamily = gliroy,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(35.dp))
            ColumnItem(
                "Username",
                "Enter your username",
                viewModel.loginTextState.value,
                { viewModel.onUpdateLoginText(it) })
//            ColumnItem(
//                "Phone Number",
//                "Enter your phone number",
//                viewModel.phoneTextState.value,
//                { viewModel.onUpdatePhoneText(it) })
            ColumnItem(
                "Password",
                "Enter your password",
                viewModel.passwordTextState.value,
                { viewModel.onUpdatePasswordText(it) })

            TextButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        ElementColor
                    )
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White,
                    fontFamily = gliroy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?")
                TextButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignIn.route)
                }) {
                    Text(text = "Sign in now", style = TextStyle(
                        color = ElementColor,
                        fontFamily = gliroy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnItem(
    text: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by remember {
        mutableStateOf(text != "Password")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text, style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontFamily = gliroy,
                fontSize = 15.5.sp
            ),
            modifier = Modifier.padding(start = 6.dp, bottom = 5.dp)
        )
        OutlinedTextField(value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(Color(0xFFE7E7E7)),
            placeholder = { Text(text = placeholder) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            maxLines = 1,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (text == "Password") {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}