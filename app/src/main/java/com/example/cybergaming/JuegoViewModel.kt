package com.example.cybergaming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JuegoViewModel (private val dao: JuegoDao): ViewModel() {

    val juegos: StateFlow<List<Juego>> = dao.obtenerjuegos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _juegosseleccionados = mutableStateFlowOf<List<Juego>>(emptyList())
    val juegosseleccionados: StateFlow<List<Juego>> = _juegosseleccionados


    //corrutinas para insertar un juego en la base de datos

    //funcion para insertar un juego en la base de datos

    fun insertarJuego(juego: Juego) {
        viewModelScope.launch {
            dao.insertJuego(juego)

        }
    }

    suspend fun obtenerJuegoPorId(id: Int): Juego? {
        return dao.obtenerJuegoPorId(id)
    }


}

class JuegoViewModelFactory(private val dao: JuegoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JuegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JuegoViewModel(dao) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}