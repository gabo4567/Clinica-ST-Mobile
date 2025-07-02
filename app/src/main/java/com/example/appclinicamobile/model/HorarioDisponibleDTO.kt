package com.example.appclinicamobile.model

data class HorarioDisponibleDTO(
    val id: Int,
    val diaSemana: String,
    val horaInicio: String,
    val horaFin: String,
    val estado: String
)
