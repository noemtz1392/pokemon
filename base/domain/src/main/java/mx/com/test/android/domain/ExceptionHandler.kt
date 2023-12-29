package mx.com.test.android.domain

fun interface ExceptionHandler {
    fun handle(t: Throwable?)
}