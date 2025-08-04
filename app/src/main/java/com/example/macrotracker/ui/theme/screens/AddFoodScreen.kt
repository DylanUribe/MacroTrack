package com.example.macrotracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.macrotracker.data.FoodRepository
import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog
import com.example.macrotracker.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import com.example.macrotracker.model.FoodItemRow
import com.example.macrotracker.model.USDAApi



@Composable
fun AddFoodScreen(
    foodRepository: FoodRepository,
    onFoodAdded: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<FoodItem>()) }
    val scope = rememberCoroutineScope()


    var selectedFood by remember { mutableStateOf<FoodItem?>(null) }
    var quantity by remember { mutableStateOf("1") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery

                // Solo buscar si no está vacío para evitar consultas innecesarias
                if (newQuery.isNotBlank()) {
                    scope.launch {
                        try {
                            val response = USDAApi.retrofitService.searchFoods(newQuery)
                            results = response.foods.map { food ->
                                val nutrients = food.foodNutrients.associateBy { it.nutrientName }
                                FoodItem(
                                    name = food.description,
                                    calories = (nutrients["Energy"]?.value ?: 0.0).toInt(),
                                    protein = (nutrients["Protein"]?.value ?: 0.0).toInt(),
                                    carbs = (nutrients["Carbohydrate, by difference"]?.value ?: 0.0).toInt(),
                                    fat = (nutrients["Total lipid (fat)"]?.value ?: 0.0).toInt()
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()  // Muestra el error en Logcat
                            results = emptyList()
                        }
                    }
                } else {
                    // Si el query está vacío, limpiar resultados
                    results = emptyList()
                }
            },
            label = { Text("Buscar alimento") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(results) { food ->
                FoodItemRow(food = food, onClick = {
                    selectedFood = food
                    quantity = "1"
                })
            }
        }
    }

    if (selectedFood != null) {
        AlertDialog(
            onDismissRequest = { selectedFood = null },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        val savedFoodId = foodRepository.addFood(selectedFood!!)

                        val log = FoodLog(
                            foodId = savedFoodId.toInt(), // Convertir de Long a Int si es necesario
                            quantity = quantity.toIntOrNull() ?: 1,
                            date = DashboardViewModel.todayDate()
                        )
                        foodRepository.addFoodLog(log)
                        selectedFood = null
                        onFoodAdded()
                    }
                }) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedFood = null }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Agregar ${selectedFood?.name}") },
            text = {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

