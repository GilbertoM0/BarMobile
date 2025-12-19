package com.example.appbarfanny.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appbarfanny.data.model.OrderItem
import com.example.appbarfanny.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillSummaryView(navController: NavController, homeViewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen de Cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(homeViewModel.orderItems) { item ->
                    OrderItemRow(item = item)
                }
            }
            TotalSummary(homeViewModel = homeViewModel)
        }
    }
}

@Composable
fun OrderItemRow(item: OrderItem) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("${item.quantity}x", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(16.dp))
        Text(item.bebida.nombre, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Text(String.format("$%.2f", item.subtotal), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TotalSummary(homeViewModel: HomeViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal", style = MaterialTheme.typography.titleMedium)
            Text(String.format("$%.2f", homeViewModel.orderSubtotal), style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Propina (10%)", style = MaterialTheme.typography.titleMedium)
            Text(String.format("$%.2f", homeViewModel.tipAmount), style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("Agregar 10% de propina sugerida", modifier = Modifier.weight(1f))
            Switch(checked = homeViewModel.includeTip, onCheckedChange = homeViewModel::onIncludeTipChanged)
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total Final", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(String.format("$%.2f", homeViewModel.orderTotal), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Solicitar Cuenta / Pagar", style = MaterialTheme.typography.titleMedium)
        }
    }
}
