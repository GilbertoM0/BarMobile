package com.example.appbarfanny.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appbarfanny.navegation.AppScreens
import com.example.appbarfanny.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllProductsView(navController: NavController, homeViewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todos los productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(query = homeViewModel.searchQuery, onQueryChanged = homeViewModel::onSearchQueryChanged)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(homeViewModel.filteredBebidas) { bebida ->
                    BebidaGridItem(bebida = bebida) {
                        navController.navigate(AppScreens.ProductDetailView.route + "/${bebida.id}")
                    }
                }
            }
        }
    }
}