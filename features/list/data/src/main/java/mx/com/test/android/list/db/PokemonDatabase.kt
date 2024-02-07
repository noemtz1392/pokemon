package mx.com.test.android.list.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.PokemonTypeDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.dao.TransactionRunnerDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.PokemonTypeEntity
import mx.com.test.android.list.db.entities.RemoteKeysEntity
import mx.com.test.android.list.db.entities.relations.PokemonTypesCrossRef

@Database(
    entities = [
        PokemonEntity::class,
        PokemonTypeEntity::class,
        PokemonTypesCrossRef::class,
        RemoteKeysEntity::class
    ],
    version = PokemonDatabase.DB_VERSION,
    exportSchema = true,
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonTypeDao(): PokemonTypeDao

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