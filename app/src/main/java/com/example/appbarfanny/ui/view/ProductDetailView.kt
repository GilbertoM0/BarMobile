package com.example.appbarfanny.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appbarfanny.ui.viewmodel.HomeViewModel

@Composable
fun ProductDetailView(navController: NavController, homeViewModel: HomeViewModel, bebidaId: Int) {
    val bebida = homeViewModel.getBebidaById(bebidaId)
    var quantity by remember { mutableStateOf(1) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (bebida != null) {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f) // Image takes up 40% of the screen height
                ) {
                    AsyncImage(
                        model = bebida.imagenUrl,
                        contentDescription = bebida.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Back button
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), shape = CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-20).dp), // Overlap with the image
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = bebida.nombre,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$${"%.2f".format(bebida.precio)}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Quantity Selector
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(onClick = { if (quantity > 1) quantity-- }, shape = CircleShape) {
                                Icon(Icons.Default.Remove, contentDescription = "Remove")
                            }
                            Text(
                                text = quantity.toString(),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            OutlinedButton(onClick = { quantity++ }, shape = CircleShape) {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f)) // Push button to the bottom

                        Button(
                            onClick = { 
                                homeViewModel.addToOrder(bebida, quantity)
                                homeViewModel.startOrderStatusSimulation()
                                navController.popBackStack()
                             },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Agregar a la orden", fontSize = 18.sp)
                        }
                    }
                }
            }
        } else {
            // Handle case where bebida is not found
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Producto no encontrado", color = MaterialTheme.colorScheme.onBackground)
                // Add a back button here too for better UX
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), shape = CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}