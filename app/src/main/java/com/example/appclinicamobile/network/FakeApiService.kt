package com.example.appclinicamobile.network

import com.example.appclinicamobile.model.*

class FakeApiService {

    private val usuariosSimulados = listOf(
        LoginDTO("admin", "admin123")
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

    // 🔹 Lista simulada de pacientes (4 en total)
    fun obtenerPacientes(): List<PacienteDTO> = listOf(
        PacienteDTO(5, "12345678", "Ana", "Lopez", "ana.lopez@gmail.com", "3414567890", "Femenino", "PAMI", "Activo"),
        PacienteDTO(6, "87654321", "Luis", "Martínez", "luis.martinez@gmail.com", "3416549870", "Masculino", "OSDE", "Activo"),
        PacienteDTO(7, "11223344", "María", "González", "maria.gonzalez@gmail.com", "3411234567", "Femenino", "Swiss Medical", "Activo"),
        PacienteDTO(8, "44332211", "Jorge", "Ramírez", "jorge.ramirez@gmail.com", "3417654321", "Masculino", "Galeno", "Activo")
    )

    // 🔹 Lista simulada de profesionales (8 en total, 2 por especialidad)
    fun obtenerProfesionales(): List<ProfesionalDTO> = listOf(
        ProfesionalDTO(1, "Carlos", "Fernandez", "cfernandez@clinica.com", "Cardiología", 2, "Activo"),
        ProfesionalDTO(5, "Sofía", "Rivas", "srivas@clinica.com", "Cardiología", 5, "Activo"),

        ProfesionalDTO(2, "Laura", "Gómez", "lgomez@clinica.com", "Clínica General", 1, "Activo"),
        ProfesionalDTO(6, "Martín", "Suarez", "msuarez@clinica.com", "Clínica General", 4, "Activo"),

        ProfesionalDTO(3, "Ricardo", "Pérez", "rperez@clinica.com", "Pediatría", 3, "Activo"),
        ProfesionalDTO(7, "Juliana", "Torres", "jtorres@clinica.com", "Pediatría", 6, "Activo"),

        ProfesionalDTO(4, "Verónica", "Martínez", "vmartinez@clinica.com", "Ginecología", 4, "Activo"),
        ProfesionalDTO(8, "Diego", "López", "dlopez@clinica.com", "Ginecología", 7, "Activo")
    )

    // 🔹 Lista simulada de especialidades (sin cambios)
    fun obtenerEspecialidades(): List<EspecialidadDTO> = listOf(
        EspecialidadDTO(1, "Clínica General", "Especialidad general para atención primaria.", "Activo"),
        EspecialidadDTO(2, "Cardiología", "Especialidad para enfermedades del corazón.", "Activo"),
        EspecialidadDTO(3, "Pediatría", "Especialidad para niños y adolescentes.", "Activo"),
        EspecialidadDTO(4, "Ginecología", "Especialidad para salud femenina.", "Activo")
    )

    // 🔹 Lista simulada de horarios disponibles por profesional (2 horarios por cada uno)
    fun obtenerHorariosDisponibles(profesionalId: Int): List<HorarioDisponibleDTO> {
        return when (profesionalId) {
            1 -> listOf(
                HorarioDisponibleDTO(1, "Lunes", "08:00", "12:00", "Activo"),
                HorarioDisponibleDTO(2, "Miércoles", "14:00", "18:00", "Activo")
            )
            5 -> listOf(
                HorarioDisponibleDTO(9, "Martes", "09:00", "13:00", "Activo"),
                HorarioDisponibleDTO(10, "Jueves", "15:00", "19:00", "Activo")
            )

            2 -> listOf(
                HorarioDisponibleDTO(3, "Martes", "09:00", "12:30", "Activo"),
                HorarioDisponibleDTO(4, "Viernes", "10:00", "14:00", "Activo")
            )
            6 -> listOf(
                HorarioDisponibleDTO(11, "Lunes", "08:30", "12:30", "Activo"),
                HorarioDisponibleDTO(12, "Jueves", "13:00", "17:00", "Activo")
            )

            3 -> listOf(
                HorarioDisponibleDTO(5, "Jueves", "08:00", "11:30", "Activo"),
                HorarioDisponibleDTO(6, "Viernes", "14:00", "17:00", "Activo")
            )
            7 -> listOf(
                HorarioDisponibleDTO(13, "Miércoles", "08:00", "12:00", "Activo"),
                HorarioDisponibleDTO(14, "Viernes", "10:00", "14:00", "Activo")
            )

            4 -> listOf(
                HorarioDisponibleDTO(7, "Viernes", "10:00", "13:00", "Activo"),
                HorarioDisponibleDTO(8, "Sábado", "09:00", "12:00", "Activo")
            )
            8 -> listOf(
                HorarioDisponibleDTO(15, "Lunes", "10:00", "14:00", "Activo"),
                HorarioDisponibleDTO(16, "Miércoles", "12:00", "16:00", "Activo")
            )

            else -> emptyList()
        }
    }
}

