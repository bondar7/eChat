package com.example.echat.navigation

sealed class Screen(val route: String) {
    object SignIn: Screen("signIn_screen")
    object LogIn: Screen("logIn_screen")
    object ChatsScreen: Screen("chats_screen")
    object SettingsScreen: Screen("settings_screen")
}

