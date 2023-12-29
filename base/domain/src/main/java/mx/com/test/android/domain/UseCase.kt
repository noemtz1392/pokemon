package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mx.com.test.android.domain.common.Result

abstract class UseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
    private val handler: ExceptionHandler,
) {

    suspend operator fun invoke(params: P) = try {
        withContext(dispatcher) {
            execute(params)
        }
    } catch (throwable: Throwable) {
        handler.handle(throwable)
        Result.Failure(throwable)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): Result<R>

}