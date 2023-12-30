package com.example.echat.ui.navigation_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.echat.MainViewModel
import com.example.echat.navigation.Screen
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.MainBackgroundColor

@Composable
fun BottomNavigationBar(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {
    val navigationBarItems = listOf<BottomNavigationIcon>(
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Message,
            unselectedIcon = Icons.Outlined.Message,
            route = Screen.ChatsScreen.route
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Phone,
            unselectedIcon = Icons.Outlined.Phone,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Search,
            unselectedIcon = Icons.Outlined.Search,
            route = Screen.SearchUsersScreen.route
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Group,
            unselectedIcon = Icons.Outlined.Group,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = Screen.SettingsScreen.route
        )
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MainBackgroundColor,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            navigationBarItems.forEachIndexed { index, item ->
                val isSelected = navController.currentDestination?.hierarchy?.any { it.route == item.route } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (navController.currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        if (index == 2) {
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) Color(0xFFF7F7FA) else ElementColor)) {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = if (isSelected) ElementColor else Color.White,
                                    modifier = Modifier
                                        .padding(15.dp)
                                        .size(25.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElementColor,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = MainBackgroundColor
                    )
                )
            }
        }
    }
}

data class BottomNavigationIcon(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)