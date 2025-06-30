package com.example.appclinicamobile.network

import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO

class FakeApiService {

    private val usuariosSimulados = listOf(
        LoginDTO("admin", "admin123"),
        LoginDTO("paciente1", "paciente123"),
        LoginDTO("tutor1", "tutor123")
    )

    fun login(loginDTO: LoginDTO): UsuarioAutenticadoDTO? {
        val usuarioValido = usuariosSimulados.find {
            it.nombreUsuario == loginDTO.nombreUsuario && it.contrasena == loginDTO.contrasena
        }

        return usuarioValido?.let {
            UsuarioAutenticadoDTO(
                id = 1L,
                nombreUsuario = it.nombreUsuario,
                email = "${it.nombreUsuario}@clinica.com",
                rol = when (it.nombreUsuario) {
                    "admin" -> "Admin"
                    "paciente1" -> "Paciente"
                    "tutor1" -> "Tutor"
                    else -> "Desconocido"
                },
                token = "fake-jwt-token-${it.nombreUsuario}"
            )
        }
    }
}
