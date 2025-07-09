package com.example.appclinicamobile.model

data class UsuarioAutenticadoDTO(
    val id: Long,
    val nombreUsuario: String,
    val email: String,
    val rol: String,
    val token: String
)
