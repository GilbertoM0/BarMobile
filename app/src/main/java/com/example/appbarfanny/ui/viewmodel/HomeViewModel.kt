package com.example.appbarfanny.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbarfanny.data.RetrofitClient
import com.example.appbarfanny.data.model.Bebida
import com.example.appbarfanny.data.model.OrderItem
import com.example.appbarfanny.data.model.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var allBebidas by mutableStateOf<List<Bebida>>(emptyList())
    var filteredBebidas by mutableStateOf<List<Bebida>>(emptyList())
    val homeScreenBebidas: List<Bebida>
        get() = filteredBebidas.take(6)

    var orderItems by mutableStateOf<List<OrderItem>>(emptyList())
        private set

    val orderSubtotal: Double
        get() = orderItems.sumOf { it.subtotal }

    var includeTip by mutableStateOf(true)
        private set

    val tipAmount: Double
        get() = if (includeTip) orderSubtotal * 0.10 else 0.0

    val orderTotal: Double
        get() = orderSubtotal + tipAmount

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var searchQuery by mutableStateOf("")
        private set
    var selectedTable by mutableStateOf(4) // Default table
        private set
    var orderStatus by mutableStateOf<OrderStatus?>(null)
        private set

    fun addToOrder(bebida: Bebida) {
        val existingItem = orderItems.find { it.bebida.id == bebida.id }
        if (existingItem != null) {
            existingItem.quantity++
            orderItems = orderItems.toList() // Trigger recomposition
        } else {
            orderItems = orderItems + OrderItem(bebida = bebida)
        }
    }

    fun onIncludeTipChanged(include: Boolean) {
        includeTip = include
    }

    fun startOrderStatusSimulation() {
        viewModelScope.launch {
            orderStatus = OrderStatus.RECIBIDO
            delay(3000)
            orderStatus = OrderStatus.PREPARANDO
            delay(3000)
            orderStatus = OrderStatus.EN_CAMINO
            delay(3000)
            orderStatus = OrderStatus.ENTREGADO
            delay(2000)
            orderStatus = null
        }
    }

    fun getBebidaById(id: Int): Bebida? {
        return allBebidas.find { it.id == id }
    }

    fun onTableSelected(table: Int) {
        selectedTable = table
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        filteredBebidas = if (query.isBlank()) {
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
                filteredBebidas = bebidas
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}