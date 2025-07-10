package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.RegistroTurnoDTO
import com.example.appclinicamobile.model.TurnoDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class TurnoRepositoryReal(context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun obtenerTurnos(): List<TurnoDTO> {
        return api.obtenerTurnos()
    }

    suspend fun crearTurno(dto: RegistroTurnoDTO): TurnoDTO {
        return api.crearTurno(dto)
    }

    suspend fun actualizarTurno(turno: TurnoDTO): TurnoDTO {
        return api.actualizarTurno(turno.id, turno)
    }

    suspend fun eliminarTurno(id: Int) {
        return api.eliminarTurno(id)
    }
}
