package com.example.appclinicamobile.view.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appclinicamobile.model.*
import com.example.appclinicamobile.network.FakeApiService
import com.example.appclinicamobile.repository.EspecialidadRepositoryReal
import com.example.appclinicamobile.repository.HorarioDisponibleRepositoryReal
import com.example.appclinicamobile.repository.PacienteRepositoryReal
import com.example.appclinicamobile.repository.ProfesionalRepositoryReal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun obtenerFechaProximaDelDia(nombreDia: String): String {
    val dayOfWeek = when (nombreDia.lowercase()) {
        "lunes" -> DayOfWeek.MONDAY
        "martes" -> DayOfWeek.TUESDAY
        "miércoles" -> DayOfWeek.WEDNESDAY
        "jueves" -> DayOfWeek.THURSDAY
        "viernes" -> DayOfWeek.FRIDAY
        "sábado" -> DayOfWeek.SATURDAY
        "domingo" -> DayOfWeek.SUNDAY
        else -> DayOfWeek.MONDAY
    }

    val hoy = LocalDate.now()
    val diasParaSumar = (dayOfWeek.value - hoy.dayOfWeek.value + 7) % 7
    val fechaProxima = if (diasParaSumar == 0) hoy else hoy.plusDays(diasParaSumar.toLong())

    return fechaProxima.format(DateTimeFormatter.ISO_DATE) // "YYYY-MM-DD"
}


@Composable
fun AgregarTurnoDialog(
    onDismiss: () -> Unit,
    onConfirm: (RegistroTurnoDTO) -> Unit
) {
    // val api = FakeApiService()

    val context = LocalContext.current

    val pacienteRepository = remember { PacienteRepositoryReal(context) }
    val especialidadRepository = remember { EspecialidadRepositoryReal(context) }
    val profesionalRepository = remember { ProfesionalRepositoryReal(context) }
    val horarioRepository = remember { HorarioDisponibleRepositoryReal(context) }

    var pacienteList by remember { mutableStateOf<List<PacienteDTO>>(emptyList()) }
    var especialidades by remember { mutableStateOf<List<EspecialidadDTO>>(emptyList()) }
    var profesionales by remember { mutableStateOf<List<ProfesionalDTO>>(emptyList()) }

    var dni by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var especialidadSeleccionada by remember { mutableStateOf<EspecialidadDTO?>(null) }
    var profesionalesFiltrados by remember { mutableStateOf<List<ProfesionalDTO>>(emptyList()) }
    var profesionalSeleccionado by remember { mutableStateOf<ProfesionalDTO?>(null) }

    var fechasDisponibles by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }

    var horariosDisponibles by remember { mutableStateOf<List<String>>(emptyList()) }
    var horarioSeleccionado by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        pacienteList = withContext(Dispatchers.IO) { pacienteRepository.getPacientes() }
        especialidades = withContext(Dispatchers.IO) { especialidadRepository.getEspecialidades() }
        profesionales = withContext(Dispatchers.IO) { profesionalRepository.getProfesionales() }
    }

    LaunchedEffect(profesionalSeleccionado) {
        profesionalSeleccionado?.let { prof ->
            val horarios = withContext(Dispatchers.IO) {
                horarioRepository.getHorariosDisponibles(prof.id)
            }
            fechasDisponibles = horarios.map {
                it.diaSemana to obtenerFechaProximaDelDia(it.diaSemana)
            }.distinct()
            horariosDisponibles = emptyList()
            fechaSeleccionada = null
            horarioSeleccionado = null
        }
    }


    val diasConFechas = remember(horariosDisponibles) {
        horariosDisponibles.map { diaSemana: String ->
            val fechaNum = obtenerFechaProximaDelDia(diaSemana)
            diaSemana to fechaNum
        }.distinct()
    }

    val fechasMostrar = fechasDisponibles.map { "${it.first} - ${it.second}" }

    fun buscarPacientePorDni(dni: String): PacienteDTO? {
        return pacienteList.find { it.dni == dni }
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    val fechaNum = fechaSeleccionada?.substringAfter(" - ") ?: ""
                    val paciente = pacienteList.find { it.dni == dni }
                    val profesional = profesionalSeleccionado

                    if (paciente != null && profesional != null && fechaSeleccionada != null && horarioSeleccionado != null) {
                        val fechaNum = fechaSeleccionada!!.substringAfter(" - ")
                        val fechaHora = "${fechaNum}T${horarioSeleccionado}:00"

                        val registro = RegistroTurnoDTO(
                            idPaciente = paciente.id,
                            idProfesional = profesional.id,
                            fechaHora = fechaHora,
                            duracion = 30,
                            observaciones = "Turno solicitado desde app mobile"
                        )
                        Log.d("AgregarTurno", "Paciente ID: ${paciente.id}, Profesional ID: ${profesional.id}, FechaHora: $fechaHora")

                        onConfirm(registro)
                    } else {
                        Toast.makeText(context, "Faltan datos válidos para registrar el turno", Toast.LENGTH_LONG).show()
                    }

                },
                enabled = dni.isNotBlank() && profesionalSeleccionado != null &&
                        fechaSeleccionada != null && horarioSeleccionado != null
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Nuevo Turno") },
        text = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            )
            {

                OutlinedTextField(
                    value = dni,
                    onValueChange = {
                        dni = it
                        val paciente = buscarPacientePorDni(it)
                        if (paciente != null) {
                            nombre = paciente.nombre
                            apellido = paciente.apellido
                            telefono = paciente.telefono
                        } else {
                            nombre = ""
                            apellido = ""
                            telefono = ""
                        }
                    },
                    label = { Text("DNI del paciente") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {},
                    label = { Text("Nombre") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = {},
                    label = { Text("Apellido") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = {},
                    label = { Text("Teléfono") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(16.dp))

                // Especialidad
                Text("Especialidad")
                DropdownMenuBox(
                    items = especialidades.map { it.nombre },
                    selectedItem = especialidadSeleccionada?.nombre,
                    onItemSelected = { selected ->
                        especialidadSeleccionada = especialidades.find { it.nombre == selected }
                        profesionalesFiltrados = profesionales.filter {
                            it.idEspecialidad == especialidadSeleccionada?.id
                        }
                        profesionalSeleccionado = null
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                val coroutineScope = rememberCoroutineScope()

                // Profesional
                Text("Profesional")

                DropdownMenuBox(
                    items = profesionalesFiltrados.map { "${it.nombre} ${it.apellido}" },
                    selectedItem = profesionalSeleccionado?.let { "${it.nombre} ${it.apellido}" },
                    onItemSelected = { selected ->
                        profesionalSeleccionado = profesionalesFiltrados.find {
                            "${it.nombre} ${it.apellido}" == selected
                        }

                        // Ejecutar la carga de horarios con corutinas
                        coroutineScope.launch {
                            profesionalSeleccionado?.let { prof ->
                                val horarios = withContext(Dispatchers.IO) {
                                    horarioRepository.getHorariosDisponibles(prof.id)
                                }

                                fechasDisponibles = horarios
                                    .map { it.diaSemana to obtenerFechaProximaDelDia(it.diaSemana) }
                                    .distinct()

                                horariosDisponibles = emptyList()
                                fechaSeleccionada = null
                                horarioSeleccionado = null
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha
                Text("Fecha")
                DropdownMenuBox(
                    items = fechasMostrar,
                    selectedItem = fechaSeleccionada,
                    onItemSelected = { selected ->
                        fechaSeleccionada = selected
                        val fechaNum = selected.substringAfter(" - ")

                        coroutineScope.launch {
                            profesionalSeleccionado?.let { prof ->
                                val horarios = withContext(Dispatchers.IO) {
                                    horarioRepository.getHorariosDisponibles(prof.id)
                                }
                                horariosDisponibles = horarios
                                    .filter {
                                        obtenerFechaProximaDelDia(it.diaSemana) == fechaNum
                                    }
                                    .map { it.horaInicio }
                            }
                        }
                    }
                )


                Spacer(modifier = Modifier.height(8.dp))

                // Horario
                Text("Horario")
                DropdownMenuBox(
                    items = horariosDisponibles,
                    selectedItem = horarioSeleccionado,
                    onItemSelected = { horarioSeleccionado = it }
                )
            }
        }
    )
}

@Composable
fun DropdownMenuBox(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedItem ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            label = { Text("Seleccionar") }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
