package com.example.appclinicamobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclinicamobile.viewmodel.TurnoViewModel
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.view.components.AgregarTurnoDialog
import com.example.appclinicamobile.view.components.EditarTurnoDialog

@Composable
fun TurnosScreen(viewModel: TurnoViewModel = viewModel()) {
    val turnos by viewModel.turnos.observeAsState(emptyList())
    var mostrarDialogo by remember { mutableStateOf(false) }
    var mensajeDialogo by remember { mutableStateOf<String?>(null) } // Para mensajes centrados
    var turnoSeleccionadoParaEditar by remember { mutableStateOf<TurnoDTO?>(null) }
    var turnoSeleccionadoParaEliminar by remember { mutableStateOf<TurnoDTO?>(null) }

    var turnoSeleccionadoParaVerDetalle by remember { mutableStateOf<TurnoDTO?>(null) }
    var mostrarConfirmacionDetalle by remember { mutableStateOf(false) }
    var mostrarDetalleTurno by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lista de Turnos",
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = { mostrarDialogo = true }) {
                Text("Nuevo Turno")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(turnos) { turno ->
                TurnoCard(
                    turno,
                    onClick = {
                        turnoSeleccionadoParaVerDetalle = it
                        mostrarConfirmacionDetalle = true
                    },
                    onEditarClick = { turnoSeleccionado -> turnoSeleccionadoParaEditar = turnoSeleccionado },
                    onEliminarClick = { turnoSeleccionado -> turnoSeleccionadoParaEliminar = turnoSeleccionado }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }


    }

    if (mostrarDialogo) {
        AgregarTurnoDialog(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { nuevoTurno ->
                val exito = try {
                    viewModel.agregarTurnoSimulado(nuevoTurno)
                    true
                } catch (e: Exception) {
                    false
                }

                mostrarDialogo = false
                mensajeDialogo = if (exito) "Turno agregado con éxito" else "No se pudo agregar el turno"
            }
        )
    }

    if (turnoSeleccionadoParaEditar != null) {
        EditarTurnoDialog(
            turno = turnoSeleccionadoParaEditar!!,
            onDismiss = { turnoSeleccionadoParaEditar = null },
            onConfirm = { turnoEditado ->
                viewModel.actualizarTurnoSimulado(turnoEditado)
                turnoSeleccionadoParaEditar = null
                mensajeDialogo = "Turno actualizado con éxito"
            }
        )
    }


    if (mensajeDialogo != null) {
        MensajeCentradoDialog(
            mensaje = mensajeDialogo!!,
            onDismiss = { mensajeDialogo = null }
        )
    }

    if (turnoSeleccionadoParaEliminar != null) {
        ConfirmarEliminarDialog(
            turno = turnoSeleccionadoParaEliminar!!,
            onConfirmar = {
                viewModel.eliminarTurnoSimulado(turnoSeleccionadoParaEliminar!!)
                mensajeDialogo = "Turno eliminado con éxito"
                turnoSeleccionadoParaEliminar = null
            },
            onCancelar = {
                turnoSeleccionadoParaEliminar = null
            }
        )
    }

    if (mostrarConfirmacionDetalle && turnoSeleccionadoParaVerDetalle != null) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionDetalle = false },
            title = { Text("Ver detalle del turno") },
            text = { Text("¿Querés ver la duración y observaciones del turno?") },
            confirmButton = {
                Button(onClick = {
                    mostrarConfirmacionDetalle = false
                    mostrarDetalleTurno = true  // Estado para mostrar detalle, que creamos a continuación
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    mostrarConfirmacionDetalle = false
                    turnoSeleccionadoParaVerDetalle = null
                }) {
                    Text("No")
                }
            }
        )
    }

    if (mostrarDetalleTurno && turnoSeleccionadoParaVerDetalle != null) {
        DetalleTurnoDialog(
            turno = turnoSeleccionadoParaVerDetalle!!,
            onDismiss = {
                mostrarDetalleTurno = false
                turnoSeleccionadoParaVerDetalle = null
            }
        )
    }



}

@Composable
fun TurnoCard(
    turno: TurnoDTO,
    onClick: (TurnoDTO) -> Unit,      // Nuevo para click en toda la card
    onEditarClick: (TurnoDTO) -> Unit,
    onEliminarClick: (TurnoDTO) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(turno) },  // Aquí capturamos click en la card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Comprobante: ${turno.comprobante}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fecha y Hora: ${turno.fechaHora}")
            Text(text = "Estado: ${turno.estado}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}")
            Text(text = "Profesional: ${turno.profesional.nombre} ${turno.profesional.apellido}")
            Text(text = "Especialidad: ${turno.profesional.especialidad}")
            // No mostrar duración ni observaciones aquí
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { onEliminarClick(turno) }) {
            Text("Eliminar", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(onClick = { onEditarClick(turno) }) {
            Text("Editar")
        }
    }
}


@Composable
fun MensajeCentradoDialog(
    mensaje: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        }
    }
}

@Composable
fun ConfirmarEliminarDialog(
    turno: TurnoDTO,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Querés eliminar el turno del paciente ${turno.paciente.nombre} ${turno.paciente.apellido}?") },
        confirmButton = {
            Button(onClick = onConfirmar) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DetalleTurnoDialog(
    turno: TurnoDTO,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Detalle del turno") },
        text = {
            Column {
                Text("Comprobante: ${turno.comprobante}")
                Text("Fecha y Hora: ${turno.fechaHora}")
                Text("Estado: ${turno.estado}")
                Text("Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}")
                Text("Profesional: ${turno.profesional.nombre} ${turno.profesional.apellido}")
                Text("Especialidad: ${turno.profesional.especialidad}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Duración: ${turno.duracion} minutos")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Observaciones: ${turno.observaciones.ifBlank { "Sin observaciones" }}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}


