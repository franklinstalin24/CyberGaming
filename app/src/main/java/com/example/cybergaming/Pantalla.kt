package com.example.cybergaming

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun Pantalla(navController: NavController, viewModel: JuegoViewModel) {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Catalogo de juegos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        //usar lazycolumn para mostrar la lista de juegos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.juegos) { juego ->
                ItemJuego(juego = juego, navController = navController)

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
                "Desarrollador: ${juego.desarrollador}| Año: ${juego.año} | Genero: ${juego.genero} | Calificacion: ${juego.calificacion}",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )


        }

    }

}

