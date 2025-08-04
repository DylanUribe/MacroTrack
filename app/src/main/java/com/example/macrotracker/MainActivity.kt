package com.example.macrotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.macrotracker.data.AppDatabase
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.data.RepositoryProvider
import com.example.macrotracker.data.UserRepository
import com.example.macrotracker.navigation.AppNavigation
import com.example.macrotracker.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtén instancia de base de datos
        val database = AppDatabase.getDatabase(applicationContext)
        RepositoryProvider.foodRepository = FoodRepository(
            foodDao = database.foodDao(),
            foodLogDao = database.foodLogDao()
        )

        // Crea repositorios
        val userRepository = UserRepository(database.userDao())
        val foodRepository = FoodRepository(
            foodDao = database.foodDao(),
            foodLogDao = database.foodLogDao()
        )

        // Instancia ViewModel de autenticación
        authViewModel = AuthViewModel(userRepository)

        // Inicia la navegación de la app
        setContent {
            MaterialTheme {
                AppNavigation(authViewModel = authViewModel, foodRepository = foodRepository)
            }
        }
    }
}
