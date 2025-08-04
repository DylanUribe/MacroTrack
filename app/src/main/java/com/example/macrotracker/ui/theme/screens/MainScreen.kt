package com.example.macrotracker.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.macrotracker.data.AppDatabase
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.data.RepositoryProvider
import com.example.macrotracker.navigation.BottomNavItem
import com.example.macrotracker.navigation.BottomNavigationBar
import com.example.macrotracker.viewmodel.AuthViewModel
import com.example.macrotracker.viewmodel.DashboardViewModel
import com.example.macrotracker.viewmodel.DashboardViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(authViewModel: AuthViewModel, onLogout: () -> Unit) {
    val navController: NavHostController = rememberNavController()
    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val foodRepository = FoodRepository(
        foodDao = database.foodDao(),
        foodLogDao = database.foodLogDao()
    )

    val userId = authViewModel.currentUser?.id
    // Creamos dashboardVM solo si hay userId válido
    val dashboardVM = if (userId != null) {
        viewModel<DashboardViewModel>(
            key = "DashboardViewModel_$userId",
            factory = DashboardViewModelFactory(userId, foodRepository)        )
    } else null

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Dashboard.route) {
                if (dashboardVM != null) {
                    DashboardScreen(
                        viewModel = dashboardVM,
                        onAddFoodClick = {
                            navController.navigate(BottomNavItem.AddFood.route)
                        }
                    )
                }
            }

            composable(BottomNavItem.AddFood.route) {
                if (dashboardVM != null) {
                    AddFoodScreen(
                        foodRepository = foodRepository,
                        authViewModel = authViewModel,
                        dashboardViewModel = dashboardVM, // ← esta línea es la que faltaba
                        onFoodAdded = {
                            dashboardVM.refreshData()
                            navController.navigate(BottomNavItem.Dashboard.route) {
                                popUpTo(BottomNavItem.Dashboard.route) { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}

