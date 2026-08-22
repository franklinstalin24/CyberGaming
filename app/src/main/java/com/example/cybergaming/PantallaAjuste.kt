package com.example.cybergaming

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAjuste(navController: NavController) {
    val context = LocalContext.current
    val ajusteUsuario = remember { AjusteUsuario(context) }
    val coroutineScope = rememberCoroutineScope()
    var nombreInput by remember { mutableStateOf("") }

    // Estados para la funcionalidad de la cámara y permisos
    var imagenCapturada by remember { mutableStateOf<Bitmap?>(null) }
    var permisoRechazado by remember { mutableStateOf(false) }

    // Launcher para abrir la cámara y recibir la foto
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            imagenCapturada = bitmap
            Toast.makeText(context, "Foto de perfil capturada", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para solicitar el permiso de la cámara en tiempo de ejecución
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permisoRechazado = false
            cameraLauncher.launch()
        } else {
            permisoRechazado = true
            Toast.makeText(
                context,
                "Permiso de cámara denegado por el usuario",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración de Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sección de la Cámara y Avatar
            Text("Foto de Perfil", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            imagenCapturada?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto capturada",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    when {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            // Ya cuenta con el permiso concedido
                            cameraLauncher.launch()
                        }
                        else -> {
                            // Solicita el permiso nativo en tiempo de ejecución
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (imagenCapturada == null) "Tomar Foto con Cámara" else "Cambiar Fotografía")
            }

            // Manejo visual obligatorio si el usuario rechaza el permiso
            if (permisoRechazado) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⚠️ Permiso de cámara denegado. Habilítalo en la configuración de la app.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Configuración de Nombre (DataStore)
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text("Nombre de Usuario:", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = nombreInput,
                    onValueChange = { nombreInput = it },
                    label = { Text("Tu alias de CyberGaming") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (nombreInput.isNotBlank()) {
                            coroutineScope.launch {
                                ajusteUsuario.guardarNombre(nombreInput)
                                Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(context, "Ingresa un nombre válido", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios de Perfil")
                }
            }
        }
    }
}