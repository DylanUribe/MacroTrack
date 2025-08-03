package com.example.macrotracker.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.macrotracker.viewmodel.AuthViewModel

@Composable
fun HomeScreen(authViewModel: AuthViewModel, onProfileClick: () -> Unit) {
    val user = authViewModel.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Bienvenido, ${user?.username ?: "Usuario"}", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tus datos actuales:")
        Text("Edad: ${user?.age} años")
        Text("Peso: ${user?.weight} kg")
        Text("Estatura: ${user?.height} cm")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onProfileClick, modifier = Modifier.fillMaxWidth()) {
            Text("Editar Perfil")
        }
    }
}
