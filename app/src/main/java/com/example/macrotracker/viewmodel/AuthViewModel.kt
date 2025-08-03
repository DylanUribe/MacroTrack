package com.example.macrotracker.viewmodel

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.macrotracker.data.UserRepository
import com.example.macrotracker.model.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState



class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            currentUser = repository.loginUser(email, password)
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            currentUser = user
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionsScreen(onPermissionsGranted: () -> Unit) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(true) {
        permissionsState.launchMultiplePermissionRequest()
    }

    if (permissionsState.allPermissionsGranted) {
        onPermissionsGranted()
    } else {
        Text("Se necesitan permisos para continuar")
    }
}
