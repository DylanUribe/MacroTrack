package com.example.macrotracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.macrotracker.data.FoodRepository

class DashboardViewModelFactory(
    private val userId: Int,
    private val foodRepository: FoodRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(userId, foodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
