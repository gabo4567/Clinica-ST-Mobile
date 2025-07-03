package com.example.appclinicamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appclinicamobile.model.ProfesionalDTO
import com.example.appclinicamobile.network.FakeApiService

class ProfesionalViewModel : ViewModel() {

    private val apiService = FakeApiService()

    private val _profesionales = MutableLiveData<List<ProfesionalDTO>>()
    val profesionales: LiveData<List<ProfesionalDTO>> = _profesionales

    private val _profesionalesFiltrados = MutableLiveData<List<ProfesionalDTO>>()
    val profesionalesFiltrados: LiveData<List<ProfesionalDTO>> = _profesionalesFiltrados

    init {
        val lista = apiService.obtenerProfesionales()
        _profesionales.value = lista
        _profesionalesFiltrados.value = lista
    }

    fun filtrarPorTexto(texto: String) {
        val listaFiltrada = _profesionales.value?.filter {
            it.apellido.contains(texto, ignoreCase = true) ||
                    it.nombre.contains(texto, ignoreCase = true)
        } ?: emptyList()
        _profesionalesFiltrados.value = listaFiltrada
    }
}
