package com.example.logonrmlocal.pokemonapp.api

import com.example.logonrmlocal.pokemonapp.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    @GET("/api/v2/pokemon/{id}")
    fun getPokemonById(@Path("id") pokemonID: Int) : Call<Pokemon>

}