package com.example.appclinicamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import com.example.appclinicamobile.repository.AuthRepository

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _usuarioAutenticado = MutableLiveData<UsuarioAutenticadoDTO?>()
    val usuarioAutenticado: LiveData<UsuarioAutenticadoDTO?> = _usuarioAutenticado

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    fun login(nombreUsuario: String, contrasena: String) {
        val loginDTO = LoginDTO(nombreUsuario, contrasena)
        val resultado = repository.login(loginDTO)

        if (resultado != null) {
            _usuarioAutenticado.value = resultado
            _error.value = null
        } else {
            _usuarioAutenticado.value = null
            _error.value = "Usuario o contraseña incorrectos"
        }
    }

    fun cerrarSesion() {
        _usuarioAutenticado.value = null
    }

}
