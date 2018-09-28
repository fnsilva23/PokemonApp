package com.example.logonrmlocal.pokemonapp.model

import com.google.gson.annotations.SerializedName

data class Pokemon (

    @SerializedName("name") val nome: String,
    @SerializedName("sprites")val sprites: Sprites

)