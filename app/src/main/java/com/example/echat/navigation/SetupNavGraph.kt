package com.example.echat.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.echat.MainViewModel
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.server.data.model.User
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.ui.screens.authentication.login_screen.LoginScreen
import com.example.echat.ui.screens.authentication.signup_screen.SignInScreen
import com.example.echat.ui.screens.chats_screen.ChatsScreen
import com.example.echat.ui.screens.authentication.auth_edit_screens.EditBioScreen
import com.example.echat.ui.screens.authentication.auth_edit_screens.EditEmailScreen
import com.example.echat.ui.screens.authentication.auth_edit_screens.EditNameScreen
import com.example.echat.ui.screens.authentication.auth_edit_screens.EditPasswordScreen
import com.example.echat.ui.screens.authentication.auth_edit_screens.EditUsernameScreen
import com.example.echat.ui.screens.chat_screen.ChatScreen
import com.example.echat.ui.screens.chats_screen.ChatsViewModel
import com.example.echat.ui.screens.detailed_user_screen.DetailedUserScreen
import com.example.echat.ui.screens.search_users_screen.SearchUsersScreen
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import com.example.echat.ui.screens.settings_screen.SettingsScreen
import com.google.gson.Gson

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    prefs: SharedPreferences,
    mainViewModel: MainViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    searchUsersViewModel: SearchUsersViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel(),
    chatsViewModel: ChatsViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = if (getUser(prefs) == null) Screen.SignIn.route else Screen.ChatsScreen.route
    ) {
        composable(
            Screen.SignIn.route,
            exitTransition = {
                fadeOut(tween(700))
            },
            enterTransition = { slideInHorizontally }
        ) {
            SignInScreen(navController = navController)
        }
        composable(
            Screen.LogIn.route,
            exitTransition = {
                fadeOut(tween(700))
            },
            enterTransition = { slideInHorizontally },
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            Screen.ChatsScreen.route,
        ) {
            ChatsScreen(
                mainViewModel,
                authViewModel,
                chatsViewModel,
                chatViewModel,
                navController
            )
        }
        composable(
            Screen.SettingsScreen.route,
        ) {
            SettingsScreen(
                navController,
                mainViewModel
            )
        }
        composable(
            Screen.EditUserBioScreen.route,
        ) {
            EditBioScreen(
               navController,
                authViewModel,
                mainViewModel
            )
        }
        composable(
            Screen.EditUsernameScreen.route,
        ) {
            EditUsernameScreen(
                "Username",
                "Set username",
                navController,
                mainViewModel,
                authViewModel
            )
        }
        composable(
            Screen.EditNameScreen.route,
        ) {
            EditNameScreen(
                "Name",
                "Set name",
                navController,
                mainViewModel,
                authViewModel
            )
        }
        composable(
            Screen.EditPasswordScreen.route,
        ) {
            EditPasswordScreen(
                mainViewModel,
                authViewModel,
                navController,
            )
        }
        composable(
            Screen.EditEmailScreen.route,
        ) {
            EditEmailScreen(
                mainViewModel,
                authViewModel,
                navController,
            )
        }
        composable(
            Screen.SearchUsersScreen.route,
        ) {
            SearchUsersScreen(
                navController,
                searchUsersViewModel,
                chatViewModel
            )
        }
        composable(
            route = Screen.DetailedUserScreen.route,
        ) {
            DetailedUserScreen(
                navController = navController,
                searchUsersViewModel = searchUsersViewModel,
                chatViewModel = chatViewModel,
                mainViewModel = mainViewModel
            )
        }
        composable(
            Screen.ChatScreen.route,
        ) {
            ChatScreen(
                navController = navController,
                chatViewModel = chatViewModel
            )
        }
    }
}

private val slideOutHorizontally = slideOutHorizontally(
    targetOffsetX = { 2000 },
    animationSpec = tween(
        durationMillis = 400,
        easing = FastOutSlowInEasing
    )
)
private val slideInHorizontally = slideInHorizontally(
    initialOffsetX = { 2000 },
    animationSpec = tween(
        durationMillis = 400,
        easing = FastOutSlowInEasing
    )
)

private fun getUser(prefs: SharedPreferences): User? {
    val gson = Gson()
    val userJson = prefs.getString("USER", null)
    return if (userJson != null) {
        gson.fromJson(userJson, User::class.java)
    } else {
        null
    }
}