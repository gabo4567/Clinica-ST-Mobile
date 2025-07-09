package com.example.appclinicamobile.model

data class RegistroTurnoDTO(
    val idPaciente: Int,
    val idProfesional: Int,
    val fechaHora: String, // Formato ISO 8601, por ejemplo "2025-06-26T10:00:00"
    val duracion: Int,
    val observaciones: String
)
