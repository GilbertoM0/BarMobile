package com.example.appbarfanny.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appbarfanny.ui.preview.SplashScreen
import com.example.appbarfanny.ui.view.AllProductsView
import com.example.appbarfanny.ui.view.BillSummaryView
import com.example.appbarfanny.ui.view.HomeView
import com.example.appbarfanny.ui.preview.PreviewScreen
import com.example.appbarfanny.ui.view.ProductDetailView
import com.example.appbarfanny.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen()
            LaunchedEffect(Unit) {
                delay(3000) // Espera 3 segundos
                navController.popBackStack()
                navController.navigate(AppScreens.PreviewScreen.route)
            }
        }
        composable(AppScreens.PreviewScreen.route) {
            PreviewScreen(navController)
        }
        composable(AppScreens.HomeView.route) {
            HomeView(navController, homeViewModel)
        }
        composable(AppScreens.AllProductsView.route) {
            AllProductsView(navController, homeViewModel)
        }
        composable(AppScreens.BillSummaryView.route) {
            BillSummaryView(navController, homeViewModel)
        }
        composable(
            route = AppScreens.ProductDetailView.route + "/{bebidaId}",
            arguments = listOf(navArgument("bebidaId") { type = NavType.IntType })
        ) {
            val bebidaId = it.arguments?.getInt("bebidaId")
            requireNotNull(bebidaId)
            ProductDetailView(navController, homeViewModel, bebidaId)
        }
    }
}

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object PreviewScreen : AppScreens("preview_screen")
    object HomeView : AppScreens("home_view")
    object AllProductsView : AppScreens("all_products_view")
    object BillSummaryView : AppScreens("bill_summary_view")
    object ProductDetailView : AppScreens("product_detail_view")
}
