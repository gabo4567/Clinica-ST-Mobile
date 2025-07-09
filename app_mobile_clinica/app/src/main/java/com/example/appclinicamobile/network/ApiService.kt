package com.example.appclinicamobile.network

import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginDTO: LoginDTO): UsuarioAutenticadoDTO

    @GET("turnos")
    suspend fun obtenerTurnos(): List<TurnoDTO>

    // Agregá más endpoints reales cuando los tengas
}