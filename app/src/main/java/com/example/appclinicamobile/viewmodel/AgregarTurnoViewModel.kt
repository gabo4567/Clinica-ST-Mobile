package com.example.appclinicamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appclinicamobile.model.*
import com.example.appclinicamobile.network.FakeApiService

class AgregarTurnoViewModel : ViewModel() {

    private val api = FakeApiService()

    private val _pacienteEncontrado = MutableLiveData<PacienteDTO?>()
    val pacienteEncontrado: LiveData<PacienteDTO?> = _pacienteEncontrado

    private val _especialidades = MutableLiveData<List<EspecialidadDTO>>()
    val especialidades: LiveData<List<EspecialidadDTO>> = _especialidades

    private val _profesionalesFiltrados = MutableLiveData<List<ProfesionalDTO>>()
    val profesionalesFiltrados: LiveData<List<ProfesionalDTO>> = _profesionalesFiltrados

    private val _horariosDisponibles = MutableLiveData<List<HorarioDisponibleDTO>>()
    val horariosDisponibles: LiveData<List<HorarioDisponibleDTO>> = _horariosDisponibles

    init {
        _especialidades.value = api.obtenerEspecialidades()
    }

    fun buscarPacientePorDni(dni: String) {
        val paciente = api.obtenerPacientes().find { it.dni == dni }
        _pacienteEncontrado.value = paciente
    }

    fun filtrarProfesionalesPorEspecialidad(idEspecialidad: Int) {
        val profesionales = api.obtenerProfesionales().filter { it.idEspecialidad == idEspecialidad }
        _profesionalesFiltrados.value = profesionales
    }

    fun obtenerHorariosDelProfesional(idProfesional: Int) {
        _horariosDisponibles.value = api.obtenerHorariosDisponibles(idProfesional)
    }
}
