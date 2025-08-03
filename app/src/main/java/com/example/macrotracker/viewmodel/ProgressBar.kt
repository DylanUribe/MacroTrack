package com.example.macrotracker.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    current: Int,
    goal: Int,
    unit: String,
    barColor: Color = MaterialTheme.colorScheme.primary
) {
    val progress = (current.toFloat() / goal.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        // Texto de cantidad
        Text(
            text = "$current / $goal $unit",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Barra de progreso
        LinearProgressIndicator(
            progress = progress,
            color = barColor,
            trackColor = Color.LightGray.copy(alpha = 0.3f),
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
    }
}
