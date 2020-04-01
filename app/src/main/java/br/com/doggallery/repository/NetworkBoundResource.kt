package br.com.doggallery.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.doggallery.util.api.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val requestResult = MediatorLiveData<DataState<ViewStateType>>()

    init {
        requestResult.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            withContext(Main) {
                val apiResponse = createCall()
                requestResult.addSource(apiResponse) {
                    requestResult.removeSource(apiResponse)
                    handleNetworkCall(it)
                }
            }
        }

    }

    private fun handleNetworkCall(apiResponse: GenericApiResponse<ResponseObject>) {
        when (apiResponse) {
            is ApiSuccessResponse -> handleApiSuccessResponse(apiResponse)
            is ApiErrorResponse -> onErrorResponse(apiResponse.errorMessage)
            is ApiEmptyResponse -> onErrorResponse("Request error")
            else -> onErrorResponse("Request error. Unknown error")
        }
    }

    private fun onErrorResponse(message: String) {
        requestResult.value = DataState.error(message = message)
    }

    fun onSuccessResponse(successObject: ViewStateType) {
        requestResult.value = DataState.data(message = null, data = successObject)
    }

    fun asLiveData() = requestResult as LiveData<DataState<ViewStateType>>

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun handleApiSuccessResponse(responseObject: ApiSuccessResponse<ResponseObject>)


}