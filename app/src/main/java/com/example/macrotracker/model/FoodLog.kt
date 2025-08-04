package com.example.macrotracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_logs")
data class FoodLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodId: Int,
    val userId: Int,
    val quantity: Int,
    val date: String
)


