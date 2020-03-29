package br.com.dogrepo.repository.main

import androidx.lifecycle.LiveData
import br.com.dogrepo.api.RetrofitBuilder
import br.com.dogrepo.api.RetrofitBuilder.dogsApiService
import br.com.dogrepo.api.models.DogApiResponse
import br.com.dogrepo.repository.NetworkBoundResource
import br.com.dogrepo.ui.main.state.MainViewState
import br.com.dogrepo.util.api.ApiSuccessResponse
import br.com.dogrepo.util.api.GenericApiResponse
import br.com.dogrepo.util.extensions.forEach
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

