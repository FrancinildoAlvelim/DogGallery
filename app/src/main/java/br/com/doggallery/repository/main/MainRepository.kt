package br.com.doggallery.repository.main

import androidx.lifecycle.LiveData
import br.com.doggallery.api.RetrofitBuilder
import br.com.doggallery.api.RetrofitBuilder.dogsApiService
import br.com.doggallery.api.models.DogApiResponse
import br.com.doggallery.repository.NetworkBoundResource
import br.com.doggallery.ui.main.state.MainViewState
import br.com.doggallery.util.api.ApiSuccessResponse
import br.com.doggallery.util.api.GenericApiResponse
import br.com.doggallery.util.extensions.forEach
import org.json.JSONObject

object MainRepository {
    fun getDogsRandomly(amount: Int = RetrofitBuilder.MAX_RESULTS_PER_REQUEST) =
        object : NetworkBoundResource<DogApiResponse, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<DogApiResponse>> =
                dogsApiService.getDogsRandomly(amount)

            override fun handleApiSuccessResponse(responseObject: ApiSuccessResponse<DogApiResponse>) =
                onSuccessResponse(
                    MainViewState(
                        dogList = responseObject.body.message
                    )
                )
        }.asLiveData()


    fun getDogsByBreed(breed: String) =
        object : NetworkBoundResource<DogApiResponse, MainViewState>() {
            override fun createCall() = dogsApiService.getDogsByBreed(breed)

            override fun handleApiSuccessResponse(responseObject: ApiSuccessResponse<DogApiResponse>) =
                onSuccessResponse(
                    MainViewState(
                        dogList = responseObject.body.message
                    )
                )
        }.asLiveData()


    fun getAllBreeds() = object : NetworkBoundResource<Any, MainViewState>() {
        override fun createCall() = dogsApiService.getAllBreeds()

        override fun handleApiSuccessResponse(responseObject: ApiSuccessResponse<Any>) {
            val jsonResponseBody = JSONObject(responseObject.body.toString())
            val message = jsonResponseBody.getJSONObject("message")
            val breeds = message.names()
            var stringBreeds: List<String> = mutableListOf()

            breeds?.forEach {
                stringBreeds = stringBreeds + it
            }
            onSuccessResponse(
                MainViewState(
                    breedList = stringBreeds
                )
            )
        }
    }.asLiveData()
}

