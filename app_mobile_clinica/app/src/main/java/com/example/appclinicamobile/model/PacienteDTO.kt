package com.example.appclinicamobile.model

data class PacienteDTO(
    val id: Int,
    val dni: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val genero: String,
    val obraSocial: String?,
    val estado: String
)
