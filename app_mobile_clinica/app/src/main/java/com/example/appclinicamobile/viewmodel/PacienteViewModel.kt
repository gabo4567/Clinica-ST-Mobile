package com.example.appclinicamobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appclinicamobile.model.PacienteDTO
import com.example.appclinicamobile.repository.PacienteRepositoryReal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PacienteViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PacienteRepositoryReal(application)

    private val _todosLosPacientes = mutableListOf<PacienteDTO>()

    private val _pacientesFiltrados = MutableLiveData<List<PacienteDTO>>()
    val pacientes: LiveData<List<PacienteDTO>> = _pacientesFiltrados

    init {
        // Carga inicial desde API
        viewModelScope.launch {
            try {
                val pacientesDesdeApi = withContext(Dispatchers.IO) {
                    repo.getPacientes()
                }
                _todosLosPacientes.clear()
                _todosLosPacientes.addAll(pacientesDesdeApi)
                _pacientesFiltrados.value = pacientesDesdeApi
            } catch (e: Exception) {
                // En caso de error, podés mostrar algo vacío o loguear el error
                _pacientesFiltrados.value = emptyList()
            }
        }
    }

    fun filtrarPorDni(dni: String) {
        _pacientesFiltrados.value = if (dni.isBlank()) {
            _todosLosPacientes
        } else {
            _todosLosPacientes.filter { it.dni.contains(dni.trim(), ignoreCase = true) }
        }
    }
}
