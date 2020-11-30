package com.elliot.rickandmortyapp.net


import com.elliot.rickandmortyapp.BASE_URL
import com.elliot.rickandmortyapp.net.services.RickAndMortyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        @JvmStatic
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @JvmStatic
        public val rickAndMortyService = retrofit.create(RickAndMortyService::class.java)
    }

}