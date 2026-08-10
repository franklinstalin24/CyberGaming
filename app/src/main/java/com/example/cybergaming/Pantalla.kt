package com.example.cybergaming

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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


@Composable
fun Pantalla(navController: NavController, viewModel: JuegoViewModel) {

    // Obtenemos la lista de juegos desde el ViewModel y la convertimos en un estado observable
    val listaJuegos = viewModel.juegos.collectAsState()

    val context = LocalContext.current

    val ajusteUsuario = remember { AjusteUsuario(context) }

    val nombreUsuario by ajusteUsuario.nombreUsuarioFlow.collectAsState(initial = "Cargando...")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hola $nombreUsuario", fontWeight = FontWeight.Bold) },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(route= "ajustes")}) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val nuevoJuego = Juego(
                        nombre = "Nuevo Juego",
                        descripcion = "Descripcion del nuevo juego",
                        anio = 2024,
                        desarrollador = "Desarrollador del nuevo juego",
                        genero = "Genero del nuevo juego",
                        calificacion = 5.0,
                        urlImagen = "https://example.com/nuevo_juego.jpg"
                    )

                    viewModel.insertarJuego(nuevoJuego)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar juego")
            }
        }
    ) { paddingValues ->

        if (listaJuegos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ){
                Text("No hay juegos disponibles", fontSize = 20.sp)
            }
        } else {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items{Spacer(modifier = Modifier.height(16.dp))}
            items(listaJuegos) { juego ->
                ItemJuego(juego = juego, navController = navController)
            }
        }
    }
}

}

@Composable
fun ItemJuego(juego: Juego, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                navController.navigate("detalle/${juego.id}")
            },

        elevation = CardDefaults.cardElevation(4.dp)

    ){
        Column{
            AsyncImage(
                model = juego.urlImagen,
                contentDescription = "Poster de ${juego.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

        }
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(juego.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Descripcion: ${juego.descripcion}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Desarrollador: ${juego.desarrollador}| Año: ${juego.anio} | Genero: ${juego.genero} | Calificacion: ${juego.calificacion}",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )


        }

    }

}

