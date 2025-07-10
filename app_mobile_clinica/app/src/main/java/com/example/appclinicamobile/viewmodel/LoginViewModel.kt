package com.example.appclinicamobile.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.appclinicamobile.model.LoginDTO
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import com.example.appclinicamobile.repository.AuthRepositoryReal
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepositoryReal(application.applicationContext)

    private val _usuarioAutenticado = MutableLiveData<UsuarioAutenticadoDTO?>()
    val usuarioAutenticado: LiveData<UsuarioAutenticadoDTO?> = _usuarioAutenticado

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(
        nombreUsuario: String,
        contrasena: String,
        onLoginExitoso: ((UsuarioAutenticadoDTO) -> Unit)? = null
    ) {
        val loginDTO = LoginDTO(nombreUsuario, contrasena)

        viewModelScope.launch {
            try {
                val resultado = repository.login(loginDTO)
                _usuarioAutenticado.value = resultado
                _error.value = null
                onLoginExitoso?.invoke(resultado)
            } catch (e: Exception) {
                _usuarioAutenticado.value = null
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }

    fun cerrarSesion() {
        _usuarioAutenticado.value = null
        // También podrías eliminar el token aquí
    }
}
