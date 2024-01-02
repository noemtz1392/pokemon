package mx.com.test.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mx.com.test.android.domain.common.Result

abstract class UseCase<in P, out R>(
    private val exceptionHandler: ExceptionHandler,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: P): Result<R> {
        return try {
            withContext(dispatcher) {
                execute(params)
            }
        } catch (throwable: Throwable) {
            exceptionHandler.handle(throwable)
            Result.Failure(throwable)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): Result<R>

}