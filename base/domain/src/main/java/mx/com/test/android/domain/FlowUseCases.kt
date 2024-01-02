package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import mx.com.test.android.domain.common.Result

abstract class FlowUseCases<in P1, in P2, out R>(
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(firstParam: P1, secondParam: P2): Flow<Result<R>> {
        return execute(firstParam = firstParam, secondParam = secondParam)
            .catch { throwable ->
                exceptionHandler.handle(throwable)
                emit(Result.Failure(throwable))
            }.flowOn(dispatcher)
    }

    protected abstract fun execute(firstParam: P1, secondParam: P2): Flow<Result<R>>
}