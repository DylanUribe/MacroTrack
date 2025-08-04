package com.example.macrotracker.data

import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    val foodDao: FoodDao,
    private val foodLogDao: FoodLogDao
) {
    val foodLogsFlow: Flow<List<FoodLog>> = foodLogDao.getAllLogs()

    suspend fun addFood(food: FoodItem): Long { return foodDao.insertFood(food) }
    suspend fun addFoodLog(log: FoodLog) = foodLogDao.insert(log)
    suspend fun getLogsForUserAndDate(userId: Int, date: String): List<FoodLog> {
        return foodLogDao.getLogsByUserAndDate(userId, date)
    }
}

