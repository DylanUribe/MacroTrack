package com.example.macrotracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.model.DailyLog
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    var dailyLog by mutableStateOf(
        DailyLog(
            date = todayDate(),
            calories = 0,
            protein = 0,
            carbs = 0,
            fat = 0
        )
    )
        private set

    val calorieGoal = 2000
    val proteinGoal = 150
    val carbGoal = 250
    val fatGoal = 70

    init {
        loadDailyLog()
    }

    private fun loadDailyLog() {
        viewModelScope.launch {
            val today = todayDate()
            val logs = foodRepository.getLogsForDate(today)

            var cal = 0
            var prot = 0
            var carb = 0
            var fat = 0

            for (log in logs) {
                val food = foodRepository.foodDao.getFoodById(log.foodId)
                if (food != null) {
                    cal += food.calories * log.quantity
                    prot += food.protein * log.quantity
                    carb += food.carbs * log.quantity
                    fat += food.fat * log.quantity
                }
            }

            dailyLog = DailyLog(today, cal, prot, carb, fat)
        }
    }

    fun refreshData() = loadDailyLog()

    companion object {
        fun todayDate(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.now().format(formatter)
        }
    }
}
