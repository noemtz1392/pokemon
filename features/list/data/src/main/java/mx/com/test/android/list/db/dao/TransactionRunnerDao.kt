package mx.com.test.android.list.db.dao

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Transaction
import mx.com.test.android.data.dao.TransactionRunner

/**
 * DAO which provides the implementation for our [TransactionRunner].
 */
@Dao
interface TransactionRunnerDao : TransactionRunner {
    @Transaction
    suspend fun runInTransaction(tx: suspend () -> Unit) = tx()

    @Ignore
    override suspend fun invoke(tx: suspend () -> Unit) {
        runInTransaction(tx)
    }
}