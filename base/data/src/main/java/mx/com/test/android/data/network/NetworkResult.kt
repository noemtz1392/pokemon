package mx.com.test.android.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class NetworkResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : NetworkResult<T>()

    data class Error(val code: Int, val message: String) : NetworkResult<Nothing>()

    data class Failure(val exception: Throwable? = null) : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[code=$code, message=$message]"
            is Failure -> "Failure[exception=$exception]"
        }
    }
}

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful && response.body() != null) {
                return@withContext NetworkResult.Success(response.body()!!)
            } else {
                return@withContext NetworkResult.Error(response.code(), response.message())
            }
        } catch (throwable: Throwable) {
            return@withContext NetworkResult.Failure(throwable as Exception)
        }
    }
}