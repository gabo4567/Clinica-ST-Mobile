package com.example.appclinicamobile.network

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun guardarToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun obtenerToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun eliminarToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }
}
