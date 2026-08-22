package com.example.cybergaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    val context = LocalContext.current

    val database = JuegoDatabase.obtenerBaseDatos(context)
    val dao = database.juegoDao()
    val api = RetrofitClient.apiService

    val repositorio = remember { JuegoRepository(dao, api) }

    val juegoViewModel: JuegoViewModel = viewModel(
        factory = JuegoViewModelFactory(repositorio)
    )

    NavHost(
        navController = navController,
        startDestination = "pantalla"
    ) {
        composable("pantalla") {
            Pantalla(navController = navController, viewModel = juegoViewModel)
        }

        composable("ajustes") {
            PantallaAjuste(navController = navController)
        }

        composable("cartelera") {
            PantallaCartelera(navController = navController, viewModel = juegoViewModel)
        }

        composable("detalle/{id}") { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")
            val idInt = idString?.toIntOrNull() ?: 0

            Detalle(navController = navController, viewModel = juegoViewModel, juegoId = idInt)
        }
    }
}

@Composable
fun PantallaCartelera() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Catálogo de Videojuegos", style = MaterialTheme.typography.headlineMedium)
        // Agrega aquí el contenido de tu cartelera
    }
}
