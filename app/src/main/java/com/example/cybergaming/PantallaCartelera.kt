package com.example.cybergaming

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCartelera(navController: NavController, viewModel: JuegoViewModel) {
    val context = LocalContext.current
    val ajusteUsuario = remember { AjusteUsuario(context) }
    val nombreUsuario by ajusteUsuario.nombreUsuarioFlow.collectAsState(initial = "Gamer")

    val estadoRed by viewModel.estadoRed.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.descargarJuegosSteam()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("🛒 Tienda de $nombreUsuario", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val estado = estadoRed) {
                is UiStateRed.Cargando -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Conectando con servidores de CyberGaming...", fontSize = 16.sp)
                    }
                }
                is UiStateRed.Exito -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(estado.juegos) { juegoSteam ->
                            ItemCarteleraSteam(
                                juego = juegoSteam,
                                onAñadir = { seleccionado ->
                                    val nuevoJuegoLocal = Juego(
                                        nombre = seleccionado.name,
                                        descripcion = "Videojuego adquirido desde la tienda oficial de CyberGaming.",
                                        anio = 2026,
                                        desarrollador = "Valve / Partner Studio",
                                        genero = "PC Game / Free-to-Play",
                                        calificacion = seleccionado.rating ?: 0.0,
                                        urlImagen = seleccionado.largeCapsuleImage ?: "",
                                        resenhaPersonal = "¡Excelente título añadido a la cuenta!",
                                        usuarioPropietario = nombreUsuario // <--- Asociado al usuario activo
                                    )
                                    viewModel.insertarJuego(nuevoJuegoLocal) { insertado ->
                                        if (insertado) {
                                            Toast.makeText(context, "¡Añadido a la biblioteca de $nombreUsuario!", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        } else {
                                            Toast.makeText(context, "Este juego ya está en tu biblioteca", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
                is UiStateRed.Error -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚠️ ${estado.mensaje}",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.descargarJuegosSteam() }) {
                            Text("Reintentar Conexión")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarteleraSteam(juego: SteamGame, onAñadir: (SteamGame) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable { onAñadir(juego) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(0.7f)
            ) {
                AsyncImage(
                    model = juego.largeCapsuleImage,
                    contentDescription = juego.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Surface(
                modifier = Modifier.fillMaxWidth().weight(0.3f),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = juego.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Free to Play",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}