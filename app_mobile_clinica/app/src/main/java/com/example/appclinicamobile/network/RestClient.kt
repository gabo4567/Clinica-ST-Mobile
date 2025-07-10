package com.example.appclinicamobile.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private var retrofit: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.3:5276/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!
    }
}