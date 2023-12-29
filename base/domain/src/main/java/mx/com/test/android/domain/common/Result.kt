package mx.com.test.android.domain.common

sealed class Result<out R> {

    data class Success<out R>(val data: R) : Result<R>()
    data class Failure(val exception: Throwable? = null) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$exception]"
        }
    }

    companion object {
        fun <R> fromNullable(result: R?) = when (result) {
            null -> Failure()
            else -> Success(result)
        }
    }
}