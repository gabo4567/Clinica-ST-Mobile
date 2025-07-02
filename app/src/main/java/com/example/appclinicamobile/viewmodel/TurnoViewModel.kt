package com.example.appclinicamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appclinicamobile.model.PacienteMiniDTO
import com.example.appclinicamobile.model.ProfesionalMiniDTO
import com.example.appclinicamobile.model.RegistroTurnoDTO
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.network.FakeApiService

class TurnoViewModel : ViewModel() {
    private val _turnos = MutableLiveData<List<TurnoDTO>>()
    val turnos: LiveData<List<TurnoDTO>> = _turnos

    private val apiService = FakeApiService()

    init {
        _turnos.value = apiService.obtenerTurnos()
    }


    fun agregarTurnoSimulado(nuevo: RegistroTurnoDTO) {
        val paciente = FakeApiService().obtenerPacientes().find { it.id == nuevo.idPaciente }
        val profesional = FakeApiService().obtenerProfesionales().find { it.id == nuevo.idProfesional }

        val nuevoTurno = TurnoDTO(
            id = (_turnos.value?.size ?: 0) + 1,
            comprobante = "ST-${System.currentTimeMillis()}",
            fechaHora = nuevo.fechaHora,
            duracion = nuevo.duracion,
            estado = "Programado",
            observaciones = nuevo.observaciones,
            paciente = PacienteMiniDTO(paciente!!.id, paciente.nombre, paciente.apellido),
            profesional = ProfesionalMiniDTO(profesional!!.id, profesional.nombre, profesional.apellido, profesional.especialidad)
        )

        val listaActual = _turnos.value?.toMutableList() ?: mutableListOf()
        listaActual.add(nuevoTurno)
        _turnos.value = listaActual
    }

    fun actualizarTurnoSimulado(turnoActualizado: TurnoDTO) {
        val listaActual = _turnos.value?.toMutableList() ?: mutableListOf()
        val index = listaActual.indexOfFirst { it.id == turnoActualizado.id }
        if (index >= 0) {
            listaActual[index] = turnoActualizado
            _turnos.value = listaActual
        }
    }

    fun eliminarTurnoSimulado(turnoAEliminar: TurnoDTO) {
        val listaActual = _turnos.value?.toMutableList() ?: mutableListOf()
        val eliminado = listaActual.removeIf { it.id == turnoAEliminar.id }
        if (eliminado) {
            _turnos.value = listaActual
        }
    }


}
