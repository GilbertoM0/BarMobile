package com.example.appbarfanny.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appbarfanny.R
import com.example.appbarfanny.data.model.Bebida
import com.example.appbarfanny.navegation.AppScreens
import com.example.appbarfanny.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showCallDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        homeViewModel.loadBebidas()
    }

    val bebidas = homeViewModel.bebidasList
    val isLoading = homeViewModel.isLoading
    val error = homeViewModel.errorMessage

    Scaffold(
        topBar = { HomeHeader(homeViewModel.selectedTable) { showBottomSheet = true } },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCallDialog = true },
                containerColor = Color(0xFF6650a4)
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Llamar al mesero", tint = Color.White)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(homeViewModel.searchQuery, homeViewModel::onSearchQueryChanged)
            FindResultsHeader(itemCount = bebidas.size)
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(bebidas) { bebida ->
                            BebidaGridItem(bebida = bebida, onProductClick = {
                                navController.navigate(AppScreens.ProductDetailView.route + "/${bebida.id}")
                            })
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            TableSelectorBottomSheet(onTableSelected = {
                homeViewModel.onTableSelected(it)
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }, homeViewModel.selectedTable)
        }
    }

    if (showCallDialog) {
        AlertDialog(
            onDismissRequest = { showCallDialog = false },
            title = { Text("Aviso", color = Color.White) },
            text = { Text("¿Qué deseas hacer?", color = Color.White.copy(alpha = 0.8f)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCallDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Enviando aviso para la Mesa ${String.format("%02d", homeViewModel.selectedTable)}...")
                        }
                    }
                ) {
                    Text("Llamar al mesero")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showCallDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Enviando aviso para pedir la cuenta en la Mesa ${String.format("%02d", homeViewModel.selectedTable)}...")
                        }
                    }
                ) {
                    Text("Pedir la cuenta")
                }
            },
            containerColor = Color(0xFF2C2C2C),
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun HomeHeader(selectedTable: Int, onTableClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.home_perfil),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Hello", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Darlene", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
        Chip(onClick = onTableClick, label = { Text(String.format("Mesa %02d", selectedTable)) }, leadingIcon = {
            Icon(Icons.Default.TableRestaurant, contentDescription = "Table Icon")
        })
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text("Buscar Productos") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun FindResultsHeader(itemCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Find results ($itemCount Items)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(text = "(See All)", color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun BebidaGridItem(bebida: Bebida, onProductClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onProductClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = bebida.imagenUrl,
                    contentDescription = bebida.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(onClick = { /* TODO: Favorite action */ }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = bebida.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Size: ${bebida.tamano}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$${"%.2f".format(bebida.precio)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TableSelectorBottomSheet(onTableSelected: (Int) -> Unit, selectedTable: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Seleccionar Mesa", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(20) { table ->
                val tableNumber = table + 1
                val isSelected = tableNumber == selectedTable
                Button(
                    onClick = { onTableSelected(tableNumber) },
                    shape = CircleShape,
                    modifier = Modifier.aspectRatio(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(text = tableNumber.toString())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    MaterialTheme {
        // HomeView() // This preview won't work with NavController
    }
}

@Composable
fun Chip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            leadingIcon()
            Spacer(modifier = Modifier.width(4.dp))
            label()
        }
    }
}