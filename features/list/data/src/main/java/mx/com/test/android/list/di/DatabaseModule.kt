package mx.com.test.android.list.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.data.dao.TransactionRunner
import mx.com.test.android.list.db.PokemonDatabase
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideShiftDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return PokemonDatabase.getDatabase(context.applicationContext)
    }

    @Provides
    fun providePokemonDao(db: PokemonDatabase): PokemonDao = db.pokemonDao()

    @Provides
    fun provideRemoteKeysDao(db: PokemonDatabase): RemoteKeysDao = db.remoteKeysDao()

    @Provides
    fun provideTransactionRunner(db: PokemonDatabase): TransactionRunner = db.transactionRunnerDao()
}