package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.HorarioDisponibleDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class HorarioDisponibleRepositoryReal(private val context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun getHorariosDisponibles(profesionalId: Int): List<HorarioDisponibleDTO> {
        return api.getHorariosDisponibles(profesionalId)
    }
}
