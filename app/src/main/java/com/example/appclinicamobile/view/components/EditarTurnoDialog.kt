package com.example.appclinicamobile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appclinicamobile.model.*
import com.example.appclinicamobile.network.FakeApiService

@Composable
fun EditarTurnoDialog(
    turno: TurnoDTO,
    onDismiss: () -> Unit,
    onConfirm: (TurnoDTO) -> Unit
) {
    val api = FakeApiService()

    val especialidades = remember { api.obtenerEspecialidades() }

    // Especialidad seleccionada inicialmente (la del turno)
    var especialidadSeleccionada by remember {
        mutableStateOf(especialidades.find { it.nombre == turno.profesional.especialidad })
    }

    // Profesionales filtrados según la especialidad seleccionada
    var profesionalesFiltrados by remember {
        mutableStateOf(
            especialidadSeleccionada?.let {
                api.obtenerProfesionales().filter { prof -> prof.idEspecialidad == it.id }
            } ?: emptyList()
        )
    }

    // Profesional seleccionado inicialmente (el del turno)
    var profesionalSeleccionado by remember {
        mutableStateOf(
            profesionalesFiltrados.find {
                it.nombre == turno.profesional.nombre && it.apellido == turno.profesional.apellido
            }
        )
    }

    // Lista de fechas en formato "Día - Fecha"
    var fechasDisponibles by remember { mutableStateOf<List<String>>(emptyList()) }
    // Fecha seleccionada en formato "Día - Fecha"
    var fechaSeleccionada by remember {
        mutableStateOf<String?>(null)
    }

    // Lista de horarios disponibles
    var horariosDisponibles by remember { mutableStateOf<List<String>>(emptyList()) }
    // Horario seleccionado
    var horarioSeleccionado by remember {
        mutableStateOf<String?>(null)
    }

    // Inicializar fechasDisponibles, fechaSeleccionada, horariosDisponibles y horarioSeleccionado al iniciar el diálogo
    LaunchedEffect(profesionalSeleccionado) {
        profesionalSeleccionado?.let { prof ->
            val horarios = api.obtenerHorariosDisponibles(prof.id)

            // Armar la lista "Día - Fecha"
            fechasDisponibles = horarios
                .map { it.diaSemana to obtenerFechaProximaDelDia(it.diaSemana) }
                .distinct()
                .map { "${it.first} - ${it.second}" }

            // Intentar seleccionar la fecha del turno si corresponde
            val fechaDelTurno = turno.fechaHora.substringBefore("T")
            // Buscar la cadena completa que contenga la fecha numérica para asignarla
            fechaSeleccionada = fechasDisponibles.find { it.endsWith(fechaDelTurno) }

            // Si ya hay fecha seleccionada, cargar horarios de ese día
            if (fechaSeleccionada != null) {
                val diaSeleccionado = fechaSeleccionada!!.substringBefore(" - ")
                horariosDisponibles = horarios
                    .filter { it.diaSemana == diaSeleccionado }
                    .map { it.horaInicio }
            } else {
                horariosDisponibles = emptyList()
            }

            // Inicializar horario seleccionado del turno
            horarioSeleccionado = turno.fechaHora.substringAfter("T").substringBeforeLast(":")
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    val fechaNum = fechaSeleccionada?.substringAfter(" - ") ?: ""
                    val hora = horarioSeleccionado ?: ""
                    val turnoActualizado = TurnoDTO(
                        id = turno.id,
                        comprobante = turno.comprobante,
                        fechaHora = "${fechaNum}T${hora}:00",
                        duracion = turno.duracion,
                        estado = turno.estado,
                        observaciones = turno.observaciones,
                        paciente = turno.paciente,
                        profesional = ProfesionalMiniDTO(
                            id = profesionalSeleccionado!!.id,
                            nombre = profesionalSeleccionado!!.nombre,
                            apellido = profesionalSeleccionado!!.apellido,
                            especialidad = especialidadSeleccionada!!.nombre
                        )
                    )
                    onConfirm(turnoActualizado)
                },
                enabled = especialidadSeleccionada != null &&
                        profesionalSeleccionado != null &&
                        fechaSeleccionada != null &&
                        horarioSeleccionado != null
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Editar Turno") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Especialidad
                Text("Especialidad")
                DropdownMenuBox(
                    items = especialidades.map { it.nombre },
                    selectedItem = especialidadSeleccionada?.nombre ?: "",
                    onItemSelected = { selected ->
                        especialidadSeleccionada = especialidades.find { it.nombre == selected }
                        profesionalesFiltrados = especialidadSeleccionada?.let {
                            api.obtenerProfesionales().filter { prof -> prof.idEspecialidad == it.id }
                        } ?: emptyList()
                        profesionalSeleccionado = null

                        // Reiniciar fechas y horarios porque profesional cambió
                        fechasDisponibles = emptyList()
                        fechaSeleccionada = null
                        horariosDisponibles = emptyList()
                        horarioSeleccionado = null
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Profesional
                Text("Profesional")
                DropdownMenuBox(
                    items = profesionalesFiltrados.map { "${it.nombre} ${it.apellido}" },
                    selectedItem = profesionalSeleccionado?.let { "${it.nombre} ${it.apellido}" } ?: "",
                    onItemSelected = { selected ->
                        profesionalSeleccionado = profesionalesFiltrados.find {
                            "${it.nombre} ${it.apellido}" == selected
                        }

                        // Cuando se selecciona profesional, actualizar fechasDisponibles
                        profesionalSeleccionado?.let { prof ->
                            val horarios = api.obtenerHorariosDisponibles(prof.id)
                            fechasDisponibles = horarios
                                .map { it.diaSemana to obtenerFechaProximaDelDia(it.diaSemana) }
                                .distinct()
                                .map { "${it.first} - ${it.second}" }

                            // Resetear fecha y horarios
                            fechaSeleccionada = null
                            horariosDisponibles = emptyList()
                            horarioSeleccionado = null
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha
                Text("Fecha")
                DropdownMenuBox(
                    items = fechasDisponibles,
                    selectedItem = fechaSeleccionada ?: "",
                    onItemSelected = { selected ->
                        fechaSeleccionada = selected

                        // Al seleccionar fecha, actualizar horarios disponibles
                        val diaSeleccionado = selected.substringBefore(" - ")
                        profesionalSeleccionado?.let { prof ->
                            horariosDisponibles = api.obtenerHorariosDisponibles(prof.id)
                                .filter { it.diaSemana == diaSeleccionado }
                                .map { it.horaInicio }
                        }
                        horarioSeleccionado = null
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Horario
                Text("Horario")
                DropdownMenuBox(
                    items = horariosDisponibles,
                    selectedItem = horarioSeleccionado ?: "",
                    onItemSelected = { horarioSeleccionado = it }
                )
            }
        }
    )
}
