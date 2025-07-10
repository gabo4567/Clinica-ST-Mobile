package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.PacienteDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class PacienteRepositoryReal(private val context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun getPacientes(): List<PacienteDTO> {
        return api.getPacientes()
    }
}
