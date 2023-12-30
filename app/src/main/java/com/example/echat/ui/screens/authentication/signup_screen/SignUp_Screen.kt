package com.example.echat.ui.screens.authentication.signup_screen

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.echat.server.auth.AuthResult
import com.example.echat.navigation.Screen
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.gliroy
import com.example.echat.utils.observeAuthResultsAndNavigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val context = LocalContext.current

    val usernameError = signUpViewModel.usernameError.value
    val emailError = signUpViewModel.emailError.value
    val pwError = signUpViewModel.pwError.value

    LaunchedEffect(key1 = viewModel, key2 = context) {
        observeAuthResultsAndNavigate(
            viewModel,
            navController,
            context
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.TopEnd) {
            if (signUpViewModel.isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(27.dp),
                    color = ElementColor,
                    strokeWidth = 3.3.dp
                )
            }
        }

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
                text = "Sign Up", style = TextStyle(
                    fontFamily = gliroy,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(35.dp))
            ColumnItem(
                "Username",
                "Create your username",
                signUpViewModel.username.value
            ) { signUpViewModel.onUpdateUsername(it) }

            ErrorText(usernameError)

            ColumnItem(
                "Email address",
                "Enter your email address",
                signUpViewModel.email.value
            ) { signUpViewModel.onUpdateEmail(it) }

            ErrorText(emailError)

            ColumnItem(
                "Password",
                "Create your password",
                signUpViewModel.password.value
            ) { signUpViewModel.onUpdatePassword(it) }

            ErrorText(pwError)

            TextButton(
                onClick = { signUpViewModel.signUp() }, modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        ElementColor
                    )
            ) {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White,
                    fontFamily = gliroy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?")
                TextButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.LogIn.route)
                }) {
                    Text(text = "Log in now", style = TextStyle(
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

@Composable
private fun ErrorText(error: String) {
    if (error.isNotBlank()) {
        Text(text = error, color = Color.Red, modifier = Modifier.padding(bottom = 10.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnItem(
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
                .background(Color(0xFFF7F7FA)),
            placeholder = { Text(text = placeholder, fontSize = 16.sp, color = Color.DarkGray) },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
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
                            contentDescription = null,
                            tint = Color.DarkGray
                        )
                    }
                }
            }
        )
    }
}