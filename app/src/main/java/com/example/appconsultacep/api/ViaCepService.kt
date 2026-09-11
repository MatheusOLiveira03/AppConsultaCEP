package com.example.appconsultacep.api

import com.example.appconsultacep.model.ResponseEndereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("/ws/{cep}/json/")
 suspend fun buscarEndereco(  @Path("cep") cep: String) : ResponseEndereco
}
