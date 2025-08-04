package com.example.macrotracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(food: FoodItem): Long

    @Query("SELECT * FROM foods WHERE name LIKE :query")
    suspend fun searchFoods(query: String): List<FoodItem>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getFoodById(id: Int): FoodItem?
}

@Dao
interface FoodLogDao {
    @Insert
    suspend fun insert(log: FoodLog)

    @Query("SELECT * FROM food_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<FoodLog>>

    @Query("SELECT * FROM food_logs WHERE date = :date")
    suspend fun getLogsByDate(date: String): List<FoodLog>
}
