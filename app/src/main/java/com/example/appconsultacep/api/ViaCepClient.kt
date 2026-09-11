package com.example.appconsultacep.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br"
    val instance: ViaCepService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepService::class.java)
    }
}