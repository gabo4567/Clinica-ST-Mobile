package com.example.appclinicamobile.repository

import android.content.Context
import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import com.example.appclinicamobile.network.ApiService
import com.example.appclinicamobile.network.RestClient

class AuthRepositoryReal(private val context: Context) {

    private val api: ApiService by lazy {
        RestClient.getInstance(context).create(ApiService::class.java)
    }

    suspend fun login(loginDTO: LoginDTO): UsuarioAutenticadoDTO {
        return api.login(loginDTO)
    }
}