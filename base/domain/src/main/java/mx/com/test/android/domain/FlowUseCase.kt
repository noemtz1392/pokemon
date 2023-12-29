package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import mx.com.test.android.domain.common.Result

abstract class FlowUseCase<in P, out R>(
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(params: P) =
        execute(params).catch { throwable ->
            exceptionHandler.handle(throwable)
            emit(Result.Failure(throwable))
        }.flowOn(dispatcher)

    protected abstract suspend fun execute(params: P): Flow<Result<R>>
}