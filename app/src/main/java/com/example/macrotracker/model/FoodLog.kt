package com.example.macrotracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_logs")
data class FoodLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodId: Int,
    val date: String, // YYYY-MM-DD
    val quantity: Int // porciones o gramos (depende cómo definas)
)

