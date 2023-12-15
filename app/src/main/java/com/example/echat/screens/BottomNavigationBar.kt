package com.example.echat.screens

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
import com.example.echat.MainViewModel
import com.example.echat.ui.theme.ElementColor
import com.example.echat.ui.theme.MainBackgroundColor

@Composable
fun BottomNavigationBar(viewModel: MainViewModel) {
    val navigationBarItems = listOf<BottomNavigationIcon>(
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Message,
            unselectedIcon = Icons.Outlined.Message,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Phone,
            unselectedIcon = Icons.Outlined.Phone,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Search,
            unselectedIcon = Icons.Outlined.Search,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Group,
            unselectedIcon = Icons.Outlined.Group,
            route = ""
        ),
        BottomNavigationIcon(
            selectedIcon = Icons.Default.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = ""
        )
    )

    val selectedItemIndex = viewModel.selectedNavigationItemIndex.value

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MainBackgroundColor,

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        ) {
            navigationBarItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        viewModel.setSelectedNavigationItemIndex(index)
                        // navigate to certain screen
                    },
                    icon = {
                        if (index == 2) {
                            Box(modifier = Modifier.clip(CircleShape).background(ElementColor)) {
                                Icon(
                                    imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(15.dp).size(25.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
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