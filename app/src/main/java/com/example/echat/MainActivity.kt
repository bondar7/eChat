package com.example.echat

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.echat.navigation.SetupNavGraph
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.ui.theme.EChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val authViewModel: AuthViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()
    lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            // Perform actions during the splash screen //
        }

        setContent {
            EChatTheme {
                navController = rememberNavController()
                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                SetupNavGraph(navController = navController, prefs = prefs)
            }
        }
    }
}