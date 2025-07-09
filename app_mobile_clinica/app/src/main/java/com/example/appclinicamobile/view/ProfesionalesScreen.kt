package com.example.appclinicamobile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclinicamobile.model.ProfesionalDTO
import com.example.appclinicamobile.viewmodel.ProfesionalViewModel

@Composable
fun ProfesionalesScreen(viewModel: ProfesionalViewModel = viewModel()) {
    val profesionales by viewModel.profesionalesFiltrados.observeAsState(emptyList())
    var filtro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = filtro,
            onValueChange = {
                filtro = it
                viewModel.filtrarPorTexto(it)
            },
            label = { Text("Buscar profesional por nombre o apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(profesionales) { profesional ->
                ProfesionalItem(profesional)
            }
        }
    }
}

@Composable
fun ProfesionalItem(profesional: ProfesionalDTO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${profesional.nombre} ${profesional.apellido}")
            Text(text = "Email: ${profesional.email}")
            Text(text = "Especialidad: ${profesional.especialidad}")
            Text(text = "Estado: ${profesional.estado}")
        }
    }
}
