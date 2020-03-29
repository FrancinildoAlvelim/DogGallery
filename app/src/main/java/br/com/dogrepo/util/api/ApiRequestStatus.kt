package br.com.dogrepo.util.api

enum class ApiRequestStatus {
    LOADING,
    SUCCESS,
    ERROR,
    UNDEFINED
}

data class ApiRequestState<T>(
    var message: String? = null,
    var loading: Boolean = false,
    var data: T? = null
) {
    companion object {
        fun <T> error(
            message: String?
        ): ApiRequestState<T> = ApiRequestState(
            message = message,
            loading = false
        )

        fun <T> loading(
            loading: Boolean
        ): ApiRequestState<T> = ApiRequestState(
            loading = loading
        )

        fun <T> data(
            message: String?,
            data: T? = null
        ): ApiRequestState<T> = ApiRequestState(
            message = message,
            data = data,
            loading = false
        )
    }
}