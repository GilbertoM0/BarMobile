package com.example.appbarfanny.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appbarfanny.ui.preview.SplashScreen
import com.example.appbarfanny.ui.view.HomeView
import com.example.appbarfanny.ui.preview.PreviewScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
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
            HomeView()
        }
    }
}

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object PreviewScreen : AppScreens("preview_screen")
    object HomeView : AppScreens("home_view")
}
