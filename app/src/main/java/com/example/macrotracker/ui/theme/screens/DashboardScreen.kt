package com.example.macrotracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.macrotracker.viewmodel.DashboardViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState


@Composable
fun DashboardScreen(viewModel: DashboardViewModel, onAddFoodClick: () -> Unit) {
    val log by viewModel.dailyLog.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Resumen Diario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text("Fecha: ${log.date}", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))

        Text("Calorías", fontWeight = FontWeight.SemiBold)
        ProgressBar(current = log.calories, goal = viewModel.calorieGoal, unit = "kcal")

        Spacer(Modifier.height(16.dp))

        Text("Proteínas", fontWeight = FontWeight.SemiBold)
        ProgressBar(
            current = log.protein,
            goal = viewModel.proteinGoal,
            unit = "g",
            barColor = Color(0xFF81C784)
        )

        Spacer(Modifier.height(16.dp))

        Text("Carbohidratos", fontWeight = FontWeight.SemiBold)
        ProgressBar(
            current = log.carbs,
            goal = viewModel.carbGoal,
            unit = "g",
            barColor = Color(0xFF64B5F6)
        )

        Spacer(Modifier.height(16.dp))

        Text("Grasas", fontWeight = FontWeight.SemiBold)
        ProgressBar(
            current = log.fat,
            goal = viewModel.fatGoal,
            unit = "g",
            barColor = Color(0xFFFFB74D)
        )

        Spacer(Modifier.height(32.dp))

        Button(onClick = onAddFoodClick, modifier = Modifier.fillMaxWidth()) {
            Text("Añadir Alimento")
        }
    }
}

@Composable
fun ProgressBar(
    current: Int,
    goal: Int,
    unit: String,
    barColor: Color = MaterialTheme.colorScheme.primary
) {
    val progress = (current.toFloat() / goal).coerceIn(0f, 1f)
    Column {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = barColor,
            trackColor = Color.LightGray
        )
        Spacer(Modifier.height(4.dp))
        Text("$current / $goal $unit", style = MaterialTheme.typography.labelMedium)
    }
}
