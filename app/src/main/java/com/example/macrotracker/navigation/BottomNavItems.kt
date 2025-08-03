package com.example.macrotracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Dashboard : BottomNavItem("dashboard", Icons.Default.Home, "Dashboard")
    object AddFood : BottomNavItem("add_food", Icons.Default.AddCircle, "Añadir")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Perfil")
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.AddFood,
        BottomNavItem.Profile
    )
    NavigationBar {
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry.value?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Evita construir múltiples instancias del mismo destino
                        launchSingleTop = true
                        // PopUpTo para evitar pila excesiva
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Restore state para mejorar UX
                        restoreState = true
                    }
                }
            )
        }
    }
}

