package com.example.appclinicamobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appclinicamobile.model.RegistroTurnoDTO
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.repository.TurnoRepositoryReal
import kotlinx.coroutines.launch

class TurnoViewModel(application: Application) : AndroidViewModel(application) {

    private val _turnos = MutableLiveData<List<TurnoDTO>>()
    val turnos: LiveData<List<TurnoDTO>> = _turnos

    private val repo = TurnoRepositoryReal(application)

    init {
        viewModelScope.launch {
            try {
                _turnos.value = repo.obtenerTurnos()
            } catch (e: Exception) {
                // Manejar error
                _turnos.value = emptyList()
            }
        }
    }

    fun agregarTurno(nuevo: RegistroTurnoDTO, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val creado = repo.crearTurno(nuevo)
                val actual = _turnos.value?.toMutableList() ?: mutableListOf()
                actual.add(creado)
                _turnos.value = actual
                onResult(true)
            } catch (e: Exception) {
                Log.e("TurnoViewModel", "Error al agregar turno: ${e.message}", e)
                onResult(false)
            }
        }
    }



    fun actualizarTurno(turnoActualizado: TurnoDTO) {
        viewModelScope.launch {
            try {
                repo.actualizarTurno(turnoActualizado)
                refrescarTurnos() // fuerza refrescar lista
            } catch (e: Exception) {
                // manejar error
            }
        }
    }


    fun eliminarTurno(turno: TurnoDTO) {
        viewModelScope.launch {
            try {
                repo.eliminarTurno(turno.id)
                val actual = _turnos.value?.toMutableList() ?: mutableListOf()
                actual.removeIf { it.id == turno.id }
                _turnos.value = actual
            } catch (_: Exception) {}
        }
    }

    fun cancelarTurno(turno: TurnoDTO) {
        viewModelScope.launch {
            try {
                val turnoCancelado = turno.copy(estado = "cancelado")
                val actualizado = repo.actualizarTurno(turnoCancelado)
                val actual = _turnos.value?.toMutableList() ?: mutableListOf()
                val index = actual.indexOfFirst { it.id == actualizado.id }
                if (index != -1) {
                    actual[index] = actualizado
                    _turnos.value = actual
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun refrescarTurnos() {
        viewModelScope.launch {
            try {
                _turnos.value = repo.obtenerTurnos()
            } catch (e: Exception) {
                // manejar error
            }
        }
    }

}

