package com.example.cybergaming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class UiStateRed {
    object Cargando : UiStateRed()
    data class Exito(val juegos: List<SteamGame>) : UiStateRed()
    data class Error(val mensaje: String) : UiStateRed()
}

class JuegoViewModel(private val repository: JuegoRepository) : ViewModel() {

    fun juegosPorUsuario(usuario: String): StateFlow<List<Juego>> {
        return repository.obtenerJuegosLocales(usuario)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    private val _estadoRed = MutableStateFlow<UiStateRed>(UiStateRed.Cargando)
    val estadoRed: StateFlow<UiStateRed> = _estadoRed.asStateFlow()

    fun descargarJuegosSteam() {
        viewModelScope.launch {
            _estadoRed.value = UiStateRed.Cargando
            val resultado = repository.obtenerJuegosDestacadosDeRed()

            resultado.fold(
                onSuccess = { lista ->
                    if (lista.isNotEmpty()) {
                        _estadoRed.value = UiStateRed.Exito(lista)
                    } else {
                        _estadoRed.value = UiStateRed.Error("No se encontraron juegos disponibles en línea.")
                    }
                },
                onFailure = { _ ->
                    _estadoRed.value = UiStateRed.Error("Error de conexión: Verifique su internet.")
                }
            )
        }
    }

    fun insertarJuego(juego: Juego, onResultado: (Boolean) -> Unit) {
        viewModelScope.launch {
            val insertado = repository.insertarJuegoLocal(juego)
            onResultado(insertado)
        }
    }

    fun eliminarJuego(juego: Juego) {
        viewModelScope.launch {
            repository.eliminarJuegoLocal(juego)
        }
    }

    suspend fun obtenerJuegoPorId(id: Int): Juego? {
        return repository.obtenerJuegoLocalPorId(id)
    }
}

class JuegoViewModelFactory(private val repository: JuegoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JuegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JuegoViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}