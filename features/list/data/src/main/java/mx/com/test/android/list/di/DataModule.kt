package mx.com.test.android.list.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mx.com.test.android.data.dao.TransactionRunner
import mx.com.test.android.list.api.ApiService
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.mapper.PokemonEntityToPokemonMapper
import mx.com.test.android.list.repository.PokemonDataRepository
import mx.com.test.android.list.repository.PokemonRepository
import mx.com.test.android.list.repository.paging.PokemonRemoteMediator
import mx.com.test.android.list.source.remote.PokemonApiDataSource
import mx.com.test.android.list.source.remote.PokemonRemoteDataSource
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val PAGE_SIZE = 25

    @Singleton
    @Provides
    fun providePokemonRepository(
        pager: Pager<Int, PokemonEntity>,
        pokemonEntityToPokemonMapper: PokemonEntityToPokemonMapper,
    ): PokemonRepository = PokemonDataRepository(
        pager = pager,
        pokemonEntityToPokemonMapper = pokemonEntityToPokemonMapper
    )

    @Singleton
    @Provides
    fun providePokemonRemoteDataSource(apiService: ApiService): PokemonRemoteDataSource =
        PokemonApiDataSource(apiService = apiService)


    @Provides
    @Singleton
    fun providePokemonRemoteMediator(
        pokemonDao: PokemonDao,
        remoteKeysDao: RemoteKeysDao,
        transactionRunner: TransactionRunner,
        pokemonRemoteDataSource: PokemonRemoteDataSource,
        pokemonApiToPokemonEntityMapper: PokemonApiToPokemonEntityMapper,
        dispatcher: CoroutineDispatcher
    ): RemoteMediator<Int, PokemonEntity> = PokemonRemoteMediator(
        pokemonDao = pokemonDao,
        remoteKeysDao = remoteKeysDao,
        transactionRunner = transactionRunner,
        remoteDataSource = pokemonRemoteDataSource,
        pokemonApiToPokemonEntityMapper = pokemonApiToPokemonEntityMapper,
        dispatcher = dispatcher
    )

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(
        pokemonRemoteMediator: PokemonRemoteMediator,
        pokemonDao: PokemonDao,
    ): Pager<Int, PokemonEntity> = Pager(
        initialKey = 0,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE / 4,
            initialLoadSize = PAGE_SIZE
        ),
        remoteMediator = pokemonRemoteMediator,
        pagingSourceFactory = { pokemonDao.pagingSource() }
    )
}
