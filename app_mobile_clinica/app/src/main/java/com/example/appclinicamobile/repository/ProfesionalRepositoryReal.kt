package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.ProfesionalDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class ProfesionalRepositoryReal(private val context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun getProfesionales(): List<ProfesionalDTO> {
        return api.getProfesionales()
    }
}
