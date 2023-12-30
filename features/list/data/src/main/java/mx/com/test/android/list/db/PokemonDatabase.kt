package mx.com.test.android.list.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.dao.TransactionRunnerDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.RemoteKeysEntity

@Database(
    entities = [PokemonEntity::class, RemoteKeysEntity::class],
    exportSchema = true,
    version = PokemonDatabase.DB_VERSION
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    abstract fun remoteKeysDao(): RemoteKeysDao

    abstract fun transactionRunnerDao(): TransactionRunnerDao

    companion object {
        private const val DB_NAME = "pokemon"
        internal const val DB_VERSION = 1

        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(ctx: Context): PokemonDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(ctx, PokemonDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}