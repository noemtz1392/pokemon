package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P1, in P2, out R>(
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(firstParam: P1, secondParam: P2) =
        execute(firstParam = firstParam, secondParam = secondParam).catch { throwable ->
            exceptionHandler.handle(throwable)
        }.flowOn(dispatcher)

    protected abstract fun execute(firstParam: P1, secondParam: P2): Flow<R>
}