package com.example.cybergaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    // Crear un NavController para manejar la navegación entre pantallas
    val navController = rememberNavController()

    // Obtener el contexto actual para usarlo en la creación del ViewModel
    val context = LocalContext.current

    // Obtener la instancia de la base de datos
    val database = JuegoDatabase.getDatabase(context)
    val dao = database.juegoDao()

    val api= RetrofitClient.apiService

    val repositorio = remember { JuegoRepository(dao) }

    // Crear una instancia del ViewModel usando el factory y el DAO
    val juegoViewModel: JuegoViewModel = viewModel(
        factory = JuegoViewModelFactory(dao)
    )

    // Configurar el NavHost para definir las rutas de navegación
    NavHost(
        navController = navController,
        startDestination = "pantalla"
    ){
        // Definir la ruta para la pantalla principal
        composable("pantalla"){
            Pantalla(navController = navController, viewModel = juegoViewModel)
        }

        composable ("ajustes"){
            PantallaAjuste(navController = navController)
        }

        // Definir la ruta para la pantalla de detalle, pasando el ID del juego como argumento
        composable ("detalle/{id}") { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")

            val idInt = idString?.toIntOrNull() ?: 0

            Detalle(navController = navController, viewModel = juegoViewModel, juegoId = idInt)
            
        }
    }
}