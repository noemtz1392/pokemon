package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, out R>(
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(params: P) =
        execute(params = params)
            .catch { exceptionHandler.handle(it) }
            .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>
}