package com.example.appbarfanny.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbarfanny.data.RetrofitClient
import com.example.appbarfanny.data.model.Bebida
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var bebidasList by mutableStateOf<List<Bebida>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun loadBebidas() {
        viewModelScope.launch {
            isLoading = true
            try {
                bebidasList = RetrofitClient.authService.getBebidas()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}