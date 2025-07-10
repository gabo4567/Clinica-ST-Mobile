package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.EspecialidadDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class EspecialidadRepositoryReal(private val context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun getEspecialidades(): List<EspecialidadDTO> {
        return api.getEspecialidades()
    }
}
