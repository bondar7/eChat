package com.example.echat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.echat.auth.AuthResult
import com.example.echat.navigation.Screen
import com.example.echat.navigation.SetupNavGraph
import com.example.echat.ui.screens.authentication.AuthViewModel
import com.example.echat.ui.theme.EChatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val authViewModel: AuthViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            // Perform actions during the splash screen
        }

        setContent {
            EChatTheme {
                 navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}