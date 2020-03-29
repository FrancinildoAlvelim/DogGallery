package br.com.dogrepo.api.dogs

import androidx.lifecycle.LiveData
import br.com.dogrepo.api.models.DogApiResponse
import br.com.dogrepo.util.api.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsApiService {

    @GET("breeds/image/random/{amount}")
    fun getDogsRandomly(@Path("amount") amount: Int): LiveData<GenericApiResponse<DogApiResponse>>

    @GET("breed/{breed}/images")
    fun getDogsByBreed(
        @Path("breed") breed: String
    ): LiveData<GenericApiResponse<DogApiResponse>>

    @GET("breeds/list/all")
    fun getAllBreeds(): LiveData<GenericApiResponse<Any>>
}