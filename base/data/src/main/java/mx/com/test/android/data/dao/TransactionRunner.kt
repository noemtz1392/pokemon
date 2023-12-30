package mx.com.test.android.data.dao

/**
 * Interface with operator function which will invoke the suspending lambda within a database
 * transaction.
 */
fun interface TransactionRunner {

    suspend operator fun invoke(tx: suspend () -> Unit)
}