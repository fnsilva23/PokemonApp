package com.example.logonrmlocal.pokemonapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.example.logonrmlocal.pokemonapp.api.PokemonAPI
import com.example.logonrmlocal.pokemonapp.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient




class PesquisaActivity : AppCompatActivity() {

    var btPesquisar: Button? = null
    var etNumero: EditText? = null
    var ivPokemon: ImageView? = null
    var tvPokemon: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        initViews()

        btPesquisar?.setOnClickListener {
            val okHttp = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build();


            val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp)
                    .build()

            val api = retrofit.create(PokemonAPI::class.java)

            api.getPokemonById(etNumero?.text.toString().toInt())
                    .enqueue(object : Callback<Pokemon> {
                        override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                            Toast.makeText(this@PesquisaActivity,
                                    t?.message,
                                    Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                            if(response?.isSuccessful == true){
                                val pokemon = response.body()
                                tvPokemon?.text = pokemon?.nome
                                Picasso.get()
                                        .load(pokemon?.sprites?.frontDefault)
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.not_found)
                                        .into(ivPokemon);
                            } else {
                                Toast.makeText(this@PesquisaActivity,
                                        "Deu ruim!",
                                        Toast.LENGTH_LONG).show()
                                tvPokemon?.text = "Não encontrado"
                                ivPokemon?.setImageResource(R.drawable.not_found)
                            }
                        }

                    })
        }

    }

    fun initViews() {
        btPesquisar = findViewById(R.id.btPesquisar)
        etNumero= findViewById(R.id.etNumero)
        ivPokemon= findViewById(R.id.ivPokemon)
        tvPokemon= findViewById(R.id.tvPokemon)
    }
}


