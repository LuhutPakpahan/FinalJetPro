package robert.pakpahan.jetpro1.data.datasource.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    data class Empty<out T>(val data: T? = null) : ApiResponse<T>()
}