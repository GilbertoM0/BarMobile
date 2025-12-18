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
    private var allBebidas by mutableStateOf<List<Bebida>>(emptyList())
    var bebidasList by mutableStateOf<List<Bebida>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var searchQuery by mutableStateOf("")
        private set
    var selectedTable by mutableStateOf(4) // Default table
        private set

    fun getBebidaById(id: Int): Bebida? {
        return allBebidas.find { it.id == id }
    }

    fun onTableSelected(table: Int) {
        selectedTable = table
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        bebidasList = if (query.isBlank()) {
            allBebidas
        } else {
            allBebidas.filter { it.nombre.contains(query, ignoreCase = true) }
        }
    }

    fun loadBebidas() {
        viewModelScope.launch {
            isLoading = true
            try {
                val bebidas = RetrofitClient.authService.getBebidas()
                allBebidas = bebidas
                bebidasList = bebidas
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}