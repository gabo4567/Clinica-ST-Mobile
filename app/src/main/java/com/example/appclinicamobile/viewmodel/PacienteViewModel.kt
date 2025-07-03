package com.example.appclinicamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appclinicamobile.model.PacienteDTO
import com.example.appclinicamobile.network.FakeApiService

class PacienteViewModel : ViewModel() {

    private val apiService = FakeApiService()
    private val _todosLosPacientes = apiService.obtenerPacientes()

    private val _pacientesFiltrados = MutableLiveData<List<PacienteDTO>>()
    val pacientes: LiveData<List<PacienteDTO>> = _pacientesFiltrados

    init {
        // Mostrar todos los pacientes al iniciar
        _pacientesFiltrados.value = _todosLosPacientes
    }

    fun filtrarPorDni(dni: String) {
        _pacientesFiltrados.value = if (dni.isBlank()) {
            _todosLosPacientes
        } else {
            _todosLosPacientes.filter { it.dni.contains(dni.trim(), ignoreCase = true) }
        }
    }
}
