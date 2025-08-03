package com.example.macrotracker.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.model.FoodItem
import kotlinx.coroutines.launch

@Composable
fun AddFoodScreen(
    foodRepository: FoodRepository,
    onFoodAdded: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<FoodItem>()) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                scope.launch {
                    results = foodRepository.searchFoods("%$query%")
                }
            },
            label = { Text("Buscar alimento") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(results) { food ->
                FoodItemRow(food = food, onClick = {
                    // Aquí mostrar diálogo para seleccionar cantidad y luego guardar FoodLog
                })
            }
        }
    }
}

@Composable
fun FoodItemRow(food: FoodItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(food.name)
        Text("${food.calories} kcal")
    }
}
