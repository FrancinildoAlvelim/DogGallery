package br.com.doggallery.api.dogs

import androidx.lifecycle.LiveData
import br.com.doggallery.api.models.DogApiResponse
import br.com.doggallery.util.api.GenericApiResponse
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