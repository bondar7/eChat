package com.example.echat.navigation

sealed class Screen(val route: String) {
    object SignIn: Screen("signIn_screen")
    object LogIn: Screen("logIn_screen")
    object ChatsScreen: Screen("chats_screen")
    object SettingsScreen: Screen("settings_screen")
    object EditUserBioScreen: Screen("edit_user_bio_screen")
    object EditUsernameScreen: Screen("edit_username_screen")
    object EditNameScreen: Screen("edit_name_screen")
    object EditPasswordScreen: Screen("edit_password_screen")
    object EditEmailScreen: Screen("edit_email_screen")
}

