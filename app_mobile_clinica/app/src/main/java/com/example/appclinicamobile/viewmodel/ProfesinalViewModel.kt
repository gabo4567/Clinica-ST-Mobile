package com.example.appclinicamobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appclinicamobile.model.ProfesionalDTO
import com.example.appclinicamobile.network.FakeApiService
import com.example.appclinicamobile.repository.ProfesionalRepositoryReal
import kotlinx.coroutines.launch

class ProfesionalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProfesionalRepositoryReal(application.applicationContext)

    private val _profesionales = MutableLiveData<List<ProfesionalDTO>>()
    val profesionales: LiveData<List<ProfesionalDTO>> = _profesionales

    private val _profesionalesFiltrados = MutableLiveData<List<ProfesionalDTO>>()
    val profesionalesFiltrados: LiveData<List<ProfesionalDTO>> = _profesionalesFiltrados

    init {
        viewModelScope.launch {
            try {
                val lista = repository.getProfesionales()
                _profesionales.value = lista
                _profesionalesFiltrados.value = lista
            } catch (e: Exception) {
                Log.e("ProfesionalViewModel", "Error al obtener profesionales: ${e.message}")
            }
        }
    }

    fun filtrarPorTexto(texto: String) {
        val listaFiltrada = _profesionales.value?.filter {
            it.apellido.contains(texto, ignoreCase = true) ||
                    it.nombre.contains(texto, ignoreCase = true)
        } ?: emptyList()
        _profesionalesFiltrados.value = listaFiltrada
    }
}

