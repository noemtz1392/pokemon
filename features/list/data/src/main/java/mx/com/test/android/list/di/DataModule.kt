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
import mx.com.test.android.list.source.local.LocalDataSource
import mx.com.test.android.list.source.local.RoomLocalDataSource
import mx.com.test.android.list.source.remote.ApiDataSource
import mx.com.test.android.list.source.remote.RemoteDataSource
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val INITIAL_OFFSET = 1
    private const val PAGE_SIZE = 25

    @Singleton
    @Provides
    fun providePokemonRepository(
        pager: Pager<Int, PokemonEntity>,
        pokemonEntityToPokemonMapper: PokemonEntityToPokemonMapper,
        localDataSource: LocalDataSource,
    ): PokemonRepository = PokemonDataRepository(
        pager = pager,
        pokemonEntityToPokemonMapper = pokemonEntityToPokemonMapper,
        localDataSource = localDataSource
    )

    @Provides
    @Singleton
    fun provideLocalDataSource(pokemonDao: PokemonDao): LocalDataSource =
        RoomLocalDataSource(pokemonDao = pokemonDao)


    @Singleton
    @Provides
    fun providePokemonRemoteDataSource(apiService: ApiService): RemoteDataSource =
        ApiDataSource(apiService = apiService)


    @Provides
    @Singleton
    fun providePokemonRemoteMediator(
        pokemonDao: PokemonDao,
        remoteKeysDao: RemoteKeysDao,
        transactionRunner: TransactionRunner,
        remoteDataSource: RemoteDataSource,
        pokemonApiToPokemonEntityMapper: PokemonApiToPokemonEntityMapper,
        dispatcher: CoroutineDispatcher
    ): RemoteMediator<Int, PokemonEntity> = PokemonRemoteMediator(
        pokemonDao = pokemonDao,
        remoteKeysDao = remoteKeysDao,
        transactionRunner = transactionRunner,
        remoteDataSource = remoteDataSource,
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
        initialKey = INITIAL_OFFSET,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = pokemonRemoteMediator,
        pagingSourceFactory = { pokemonDao.pagingSource() }
    )
}
