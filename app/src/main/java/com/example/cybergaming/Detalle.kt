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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun Detalle(navController: NavController, viewModel: JuegoViewModel, juegoId: Int) {
    var juegoState by remember { mutableStateOf<Juego?>(null) }

    LaunchedEffect(juegoId) {
        juegoState = viewModel.obtenerJuegoPorId(juegoId)
    }

    val juego = juegoState

    if (juego != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(220.dp)
            ) {
                AsyncImage(
                    model = juego.urlImagen,
                    contentDescription = "Poster de ${juego.nombre}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = juego.nombre, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Año: ${juego.anio}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Desarrollador: ${juego.desarrollador}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Género: ${juego.genero}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Calificación general: ${juego.calificacion}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Descripción:", style = MaterialTheme.typography.titleSmall)
            Text(text = juego.descripcion, style = MaterialTheme.typography.bodyMedium)

            if (juego.resenhaPersonal.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Mi Reseña Personal:", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                Text(text = juego.resenhaPersonal, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(text = "Juego no encontrado", fontSize = 24.sp, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }
}