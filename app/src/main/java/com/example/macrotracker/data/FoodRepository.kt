package com.example.macrotracker.data

import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog

class FoodRepository(
    public val foodDao: FoodDao,
    private val foodLogDao: FoodLogDao
) {
    suspend fun addFood(food: FoodItem) = foodDao.insertFood(food)
    suspend fun searchFoods(query: String) = foodDao.searchFoods(query)
    suspend fun addFoodLog(log: FoodLog) = foodLogDao.insertFoodLog(log)
    suspend fun getLogsForDate(date: String) = foodLogDao.getLogsByDate(date)
    suspend fun getFoodById(id: Int): FoodItem? = foodDao.getFoodById(id)
}
