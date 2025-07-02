package com.example.appclinicamobile.network

import com.example.appclinicamobile.model.*

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

    fun obtenerTurnos(): List<TurnoDTO> = listOf(
        TurnoDTO(
            id = 1,
            comprobante = "ST-20250624-000001",
            fechaHora = "2025-06-26T10:00:00",
            duracion = 30,
            estado = "Programado",
            observaciones = "Chequeo de rutina",
            paciente = PacienteMiniDTO(5, "Ana", "Lopez"),
            profesional = ProfesionalMiniDTO(3, "Dr. Carlos", "Fernandez", "Cardiología")
        )
    )

    // 🔹 Lista simulada de pacientes
    fun obtenerPacientes(): List<PacienteDTO> = listOf(
        PacienteDTO(5, "12345678", "Ana", "Lopez", "ana.lopez@gmail.com", "3414567890", "Femenino", "PAMI", "Activo"),
        PacienteDTO(6, "87654321", "Luis", "Martínez", "luis.martinez@gmail.com", "3416549870", "Masculino", "OSDE", "Activo")
    )

    // 🔹 Lista simulada de profesionales
    fun obtenerProfesionales(): List<ProfesionalDTO> = listOf(
        ProfesionalDTO(1, "Carlos", "Fernandez", "cfernandez@clinica.com", "Cardiología", 2, "Activo"),
        ProfesionalDTO(2, "Laura", "Gómez", "lgomez@clinica.com", "Clínica General", 1, "Activo"),
        ProfesionalDTO(3, "Ricardo", "Pérez", "rperez@clinica.com", "Pediatría", 3, "Activo"),
        ProfesionalDTO(4, "Verónica", "Martínez", "vmartinez@clinica.com", "Ginecología", 4, "Activo")
    )

    // 🔹 Lista simulada de especialidades (ordenadas por ID según base de datos)
    fun obtenerEspecialidades(): List<EspecialidadDTO> = listOf(
        EspecialidadDTO(1, "Clínica General", "Especialidad general para atención primaria.", "Activo"),
        EspecialidadDTO(2, "Cardiología", "Especialidad para enfermedades del corazón.", "Activo"),
        EspecialidadDTO(3, "Pediatría", "Especialidad para niños y adolescentes.", "Activo"),
        EspecialidadDTO(4, "Ginecología", "Especialidad para salud femenina.", "Activo")
    )

    // 🔹 Lista simulada de horarios disponibles por profesional
    fun obtenerHorariosDisponibles(profesionalId: Int): List<HorarioDisponibleDTO> {
        return when (profesionalId) {
            1 -> listOf(
                HorarioDisponibleDTO(1, "Lunes", "08:00", "12:00", "Activo"),
                HorarioDisponibleDTO(2, "Miércoles", "14:00", "18:00", "Activo")
            )
            2 -> listOf(
                HorarioDisponibleDTO(3, "Martes", "09:00", "12:30", "Activo")
            )
            3 -> listOf(
                HorarioDisponibleDTO(4, "Jueves", "08:00", "11:30", "Activo")
            )
            4 -> listOf(
                HorarioDisponibleDTO(5, "Viernes", "10:00", "13:00", "Activo")
            )
            else -> emptyList()
        }
    }
}
