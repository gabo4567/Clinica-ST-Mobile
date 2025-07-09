package com.example.appclinicamobile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO

@Composable
fun HomeScreen(
    usuario: UsuarioAutenticadoDTO,
    onVerTurnosClick: () -> Unit = {},
    onVerPacientesClick: () -> Unit = {},
    onVerProfesionalesClick: () -> Unit = {},
    onCerrarSesionClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido, ${usuario.nombreUsuario}!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rol: ${usuario.rol}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVerTurnosClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Turnos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVerPacientesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Pacientes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVerProfesionalesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Profesionales")
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onCerrarSesionClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
