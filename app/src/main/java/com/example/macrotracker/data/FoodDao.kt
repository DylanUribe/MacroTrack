package com.example.macrotracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodItem): Long

    @Query("SELECT * FROM foods WHERE name LIKE :query")
    suspend fun searchFoods(query: String): List<FoodItem>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getFoodById(id: Int): FoodItem?
}

@Dao
interface FoodLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodLog(log: FoodLog): Long

    @Query("SELECT * FROM food_logs WHERE date = :date")
    suspend fun getLogsByDate(date: String): List<FoodLog>
}
