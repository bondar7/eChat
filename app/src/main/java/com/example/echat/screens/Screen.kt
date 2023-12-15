package com.example.echat.screens

sealed class Screen(val route: String) {
    object SignIn: Screen("signIn_screen")
    object LogIn: Screen("logIn_screen")
}

