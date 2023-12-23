package com.example.echat.navigation

import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.echat.auth.AuthRepository
import com.example.echat.auth.AuthResult
import com.example.echat.ui.screens.authentication.login_screen.LoginScreen
import com.example.echat.ui.screens.authentication.signIn_Screen.SignInScreen
import com.example.echat.ui.screens.chats_screen.ChatsScreen
import com.example.echat.ui.screens.settings_screen.EditBioScreen
import com.example.echat.ui.screens.settings_screen.EditPasswordScreen
import com.example.echat.ui.screens.settings_screen.EditUsernameScreen
import com.example.echat.ui.screens.settings_screen.SettingsScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {

    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
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
            Screen.EditPasswordScreen.route,
        ) {
            EditPasswordScreen(navController = navController)
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