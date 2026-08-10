package com.example.cybergaming

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun Detalle(navController: NavController, viewModel: JuegoViewModel, juegoId: Int) {

//Estado para almacenar el juego obtenido por su ID
    var juegoState by remember { mutableStateOf<Juego?>(null) }

    // Efecto lanzado para obtener el juego por su ID cuando el Composable se monta
    LaunchedEffect(juegoId) {
        juegoState = viewModel.obtenerJuegoPorId(juegoId)

        // Guardar el juego en la base de datos si no existe
    }val juego = juegoState


    if (juego != null) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

            AsyncImage(
                model = juego.urlImagen,
                contentDescription = "Poster de ${juego.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
            Text(text = juego.nombre, style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Año: ${juego.anio}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Desarrollador: ${juego.desarrollador}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Género: ${juego.genero}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Calificación: ${juego.calificacion}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = juego.descripcion, style = MaterialTheme.typography.bodyLarge)

            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Juego no encontrado", fontSize = 24.sp, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
        }
    }

}
