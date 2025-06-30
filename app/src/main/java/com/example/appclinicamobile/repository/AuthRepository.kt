package com.example.appclinicamobile.repository

import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import com.example.appclinicamobile.network.FakeApiService

class AuthRepository(private val apiService: FakeApiService = FakeApiService()) {

    fun login(loginDTO: LoginDTO): UsuarioAutenticadoDTO? {
        return apiService.login(loginDTO)
    }
}
