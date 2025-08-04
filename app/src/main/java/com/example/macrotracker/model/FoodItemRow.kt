package com.example.macrotracker.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.macrotracker.model.FoodItem

@Composable
fun FoodItemRow(food: FoodItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = food.name)
            Text(text = "Cal: ${food.calories}, P: ${food.protein}g, C: ${food.carbs}g, F: ${food.fat}g")
        }
    }
}
