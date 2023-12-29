package com.example.echat.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.echat.server.auth.AuthResult
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.navigation.Screen

suspend fun observeAuthResultsAndNavigate(
    viewModel: AuthViewModel,
    navController: NavHostController,
    context: Context
) {
        viewModel.authResults.collect {
            when (it) {
                is AuthResult.Authorized -> {
                    navController.navigate(Screen.ChatsScreen.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You are not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
}