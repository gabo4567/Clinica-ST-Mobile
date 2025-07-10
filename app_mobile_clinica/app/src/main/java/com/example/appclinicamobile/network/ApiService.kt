package com.example.appclinicamobile.network

import com.example.appclinicamobile.model.EspecialidadDTO
import com.example.appclinicamobile.model.HorarioDisponibleDTO
import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.PacienteDTO
import com.example.appclinicamobile.model.ProfesionalDTO
import com.example.appclinicamobile.model.RegistroTurnoDTO
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginDTO: LoginDTO): UsuarioAutenticadoDTO

    @GET("turnos")
    suspend fun obtenerTurnos(): List<TurnoDTO>

    @POST("turnos")
    suspend fun crearTurno(@Body registroTurnoDTO: RegistroTurnoDTO): TurnoDTO

    @PUT("turnos/{id}")
    suspend fun actualizarTurno(@retrofit2.http.Path("id") id: Int, @Body turnoDTO: TurnoDTO): TurnoDTO

    @DELETE("turnos/{id}")
    suspend fun eliminarTurno(@retrofit2.http.Path("id") id: Int)

    @GET("pacientes")
    suspend fun getPacientes(): List<PacienteDTO>

    @GET("profesionales")
    suspend fun getProfesionales(): List<ProfesionalDTO>

    @GET("especialidades")
    suspend fun getEspecialidades(): List<EspecialidadDTO>

    @GET("HorariosDisponibles/profesional/{idProfesional}")
    suspend fun getHorariosDisponibles(@Path("idProfesional") idProfesional: Int): List<HorarioDisponibleDTO>

}