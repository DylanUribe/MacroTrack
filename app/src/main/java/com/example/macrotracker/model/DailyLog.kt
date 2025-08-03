package com.example.macrotracker.model

data class DailyLog(
    val date: String, // formato YYYY-MM-DD
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
)