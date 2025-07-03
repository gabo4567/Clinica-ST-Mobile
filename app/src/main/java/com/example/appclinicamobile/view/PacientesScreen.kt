package com.example.appclinicamobile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclinicamobile.model.PacienteDTO
import com.example.appclinicamobile.viewmodel.PacienteViewModel

@Composable
fun PacientesScreen(viewModel: PacienteViewModel = viewModel()) {
    val pacientes by viewModel.pacientes.observeAsState(emptyList())
    var filtroDni by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Pacientes",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = filtroDni,
            onValueChange = {
                filtroDni = it
                viewModel.filtrarPorDni(it)
            },
            label = { Text("Filtrar por DNI") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pacientes) { paciente ->
                PacienteItem(paciente)
            }
        }
    }
}

@Composable
fun PacienteItem(paciente: PacienteDTO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${paciente.nombre} ${paciente.apellido}")
            Text(text = "DNI: ${paciente.dni}")
            Text(text = "Teléfono: ${paciente.telefono}")
            Text(text = "Email: ${paciente.email}")
            Text(text = "Género: ${paciente.genero}")
            Text(text = "Obra Social: ${paciente.obraSocial ?: "Sin especificar"}")
            Text(text = "Estado: ${paciente.estado}")
        }
    }
}
