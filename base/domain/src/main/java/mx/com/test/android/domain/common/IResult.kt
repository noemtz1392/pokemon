package mx.com.test.android.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface IResult<out R> {

    data object Loading : IResult<Nothing>
    data class Success<R>(val data: R) : IResult<R>
    data class Failure(val exception: Throwable? = null) : IResult<Nothing>

}

fun <T> Flow<T>.asResult(): Flow<IResult<T>> = this
    .map<T, IResult<T>> { IResult.Success(it) }
    .onStart { emit(IResult.Loading) }
    .catch { emit(IResult.Failure(it)) }

fun <T> Flow<T>.toResult(): Flow<Result<T>> = this
    .map<T, Result<T>> { Result.Success(it) }
    .catch { emit(Result.Failure(it)) }

