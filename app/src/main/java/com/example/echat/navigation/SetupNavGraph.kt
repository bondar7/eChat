package com.example.echat.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.echat.data.model.Person
import com.example.echat.data.model.User
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
import com.example.echat.ui.screens.search_users_screen.DetailedUserScreen
import com.example.echat.ui.screens.search_users_screen.SearchUsersScreen
import com.example.echat.ui.screens.search_users_screen.SearchUsersViewModel
import com.example.echat.ui.screens.settings_screen.SettingsScreen
import com.google.gson.Gson

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    prefs: SharedPreferences,
    searchUsersViewModel: SearchUsersViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel()
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
            ChatsScreen(navHostController = navController)
        }
        composable(
            Screen.SettingsScreen.route,
        ) {
            SettingsScreen(
                navHostController = navController,
            )
        }
        composable(
            Screen.EditUserBioScreen.route,
        ) {
            EditBioScreen(
                navController = navController
            )
        }
        composable(
            Screen.EditUsernameScreen.route,
        ) {
            EditUsernameScreen(
                "Username",
                "Set username",
                navController
            )
        }
        composable(
            Screen.EditNameScreen.route,
        ) {
            EditNameScreen(
                "Name",
                "Set name",
                navController
            )
        }
        composable(
            Screen.EditPasswordScreen.route,
        ) {
            EditPasswordScreen(navController = navController)
        }
        composable(
            Screen.EditEmailScreen.route,
        ) {
            EditEmailScreen(navController = navController)
        }
        composable(
            Screen.SearchUsersScreen.route,
        ) {
            SearchUsersScreen(
                navController,
                searchUsersViewModel
            )
        }
        composable(
            route = Screen.DetailedUserScreen.route,
        ) {
            DetailedUserScreen(
                navController = navController,
                searchUsersViewModel = searchUsersViewModel,
                chatViewModel = chatViewModel
            )
        }
        composable(
            Screen.ChatScreen.route,
        ) {
            ChatScreen(
                navController = navController,
                searchUsersViewModel = searchUsersViewModel,
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