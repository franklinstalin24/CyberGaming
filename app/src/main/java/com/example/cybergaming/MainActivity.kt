package com.example.cybergaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cybergaming.ui.theme.CyberGamingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyberGamingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CyberGamingApp()
                }
                }
            }
        }
    }

@Composable
fun CyberGamingApp() {
    val navController = rememberNavController()

    val juegoViewModel: JuegoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "pantalla"
    ){
        composable("pantalla"){
            Pantalla(navController = navController, viewModel = juegoViewModel)
        }

        composable ("detalle/{id}") { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")

            val idInt = idString?.toIntOrNull() ?: 0

            Detalle(navController = navController, viewModel = juegoViewModel, juegoId = idInt)
            
        }
    }
}