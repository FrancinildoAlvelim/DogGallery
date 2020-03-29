package br.com.dogrepo.api

import br.com.dogrepo.api.dogs.DogsApiService
import br.com.dogrepo.util.livedata.LiveDataRetrofitCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val MAX_RESULTS_PER_REQUEST = 20
    private const val BASE_URL = "https://dog.ceo/api/"
    val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataRetrofitCallAdapterFactory())
    }

    val dogsApiService: DogsApiService by lazy {
        retrofit.build().create(DogsApiService::class.java)
    }

}