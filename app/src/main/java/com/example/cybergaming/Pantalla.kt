package com.example.cybergaming

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla(navController: NavController, viewModel: JuegoViewModel) {
    val context = LocalContext.current
    val ajusteUsuario = remember { AjusteUsuario(context) }
    val nombreUsuario by ajusteUsuario.nombreUsuarioFlow.collectAsState(initial = "Gamer")

    val listaJuegos by viewModel.juegosPorUsuario(nombreUsuario).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("🎮 CyberGaming: $nombreUsuario", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("cartelera") }) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Ir a la Tienda")
                    }
                    IconButton(onClick = { navController.navigate("ajustes") }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Perfil")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val juegoDestacado = Juego(
                        nombre = "Cyberpunk 2077",
                        descripcion = "Un RPG de mundo abierto ambientado en Night City.",
                        anio = 2020,
                        desarrollador = "CD Projekt Red",
                        genero = "RPG / Acción",
                        calificacion = 4.8,
                        urlImagen = "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg",
                        resenhaPersonal = "¡Obra maestra visual y narrativa!",
                        usuarioPropietario = nombreUsuario // <--- Asignado correctamente al usuario activo
                    )
                    viewModel.insertarJuego(juegoDestacado) { insertado ->
                        if (insertado) {
                            Toast.makeText(context, "Añadido a la biblioteca de $nombreUsuario", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "El juego ya está en tu biblioteca", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir juego rápido")
            }
        }
    ) { paddingValues ->
        if (listaJuegos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("La biblioteca de $nombreUsuario está vacía", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Explora la Tienda o usa el botón '+'", fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("Juegos de $nombreUsuario (${listaJuegos.size})", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(listaJuegos) { juego ->
                    ItemJuegoSteam(
                        juego = juego,
                        navController = navController,
                        onEliminar = {
                            viewModel.eliminarJuego(juego)
                            Toast.makeText(context, "Juego eliminado", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemJuegoSteam(juego: Juego, navController: NavController, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detalle/${juego.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = juego.urlImagen,
                contentDescription = juego.nombre,
                modifier = Modifier.width(120.dp).height(70.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(juego.nombre, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Spacer(modifier = Modifier.height(2.dp))
                Text(juego.genero, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                if (juego.resenhaPersonal.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("★ Reseña guardada", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar juego",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}