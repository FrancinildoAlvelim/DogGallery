package br.com.doggallery.util.api

data class DataState<T>(
    var message: String? = null,
    var loading: Boolean = false,
    var data: T? = null
) {
    companion object {
        fun <T> error(
            message: String?
        ): DataState<T> = DataState(
            message = message,
            loading = false
        )

        fun <T> loading(
            loading: Boolean
        ): DataState<T> = DataState(
            loading = loading
        )

        fun <T> data(
            message: String?,
            data: T? = null
        ): DataState<T> = DataState(
            message = message,
            data = data,
            loading = false
        )
    }
}