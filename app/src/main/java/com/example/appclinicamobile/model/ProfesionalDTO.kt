package com.example.appclinicamobile.model

data class ProfesionalDTO(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val especialidad: String,
    val idEspecialidad: Int,
    val estado: String
)
