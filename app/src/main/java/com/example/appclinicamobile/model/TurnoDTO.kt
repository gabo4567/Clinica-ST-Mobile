package com.example.appclinicamobile.model

data class TurnoDTO(
    val id: Int,
    val comprobante: String,
    val fechaHora: String,
    val duracion: Int,
    val estado: String,
    val observaciones: String,
    val paciente: PacienteMiniDTO,
    val profesional: ProfesionalMiniDTO
)

data class PacienteMiniDTO(
    val id: Int,
    val nombre: String,
    val apellido: String
)

data class ProfesionalMiniDTO(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val especialidad: String
)
