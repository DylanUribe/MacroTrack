package com.example.macrotracker.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.ui.theme.screens.AddFoodScreen
import com.example.macrotracker.ui.theme.screens.HomeScreen
import com.example.macrotracker.ui.theme.screens.ProfileScreen
import com.example.macrotracker.ui.theme.screens.RegisterScreen
import com.example.macrotracker.ui.theme.screens.DashboardScreen
import com.example.macrotracker.ui.theme.screens.MainScreen
import com.example.macrotracker.viewmodel.AuthViewModel
import com.example.macrotracker.viewmodel.DashboardViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Dashboard : Screen("dashboard")
    object AddFood : Screen("add_food")
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel, foodRepository: FoodRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            MainScreen(authViewModel = authViewModel)
        }


        composable(Screen.Profile.route) {
            ProfileScreen(authViewModel = authViewModel)
        }
    }
}
