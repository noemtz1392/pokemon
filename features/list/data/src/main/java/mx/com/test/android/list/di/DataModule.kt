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
import mx.com.test.android.list.db.dao.PokemonTypeDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.entities.relations.PokemonWithTypes
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.mapper.PokemonTypeApiToPokemonTypeEntityMapper
import mx.com.test.android.list.mapper.PokemonWithTypesToPokemonMapper
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

    @Provides
    @Singleton
    fun providePokemonRepository(
        pager: Pager<Int, PokemonWithTypes>,
        pokemonWithTypesToPokemonMapper: PokemonWithTypesToPokemonMapper,
        localDataSource: LocalDataSource,
    ): PokemonRepository = PokemonDataRepository(
        pager = pager,
        pokemonWithTypesToPokemonMapper = pokemonWithTypesToPokemonMapper,
        localDataSource = localDataSource
    )

    @Provides
    @Singleton
    fun provideLocalDataSource(
        pokemonDao: PokemonDao,
        pokemonWithTypesToPokemonMapper: PokemonWithTypesToPokemonMapper
    ): LocalDataSource = RoomLocalDataSource(
        pokemonDao = pokemonDao,
        pokemonWithTypesToPokemonMapper = pokemonWithTypesToPokemonMapper
    )

    @Provides
    @Singleton
    fun providePokemonRemoteDataSource(apiService: ApiService): RemoteDataSource =
        ApiDataSource(apiService = apiService)


    @Provides
    @Singleton
    fun providePokemonRemoteMediator(
        pokemonDao: PokemonDao,
        pokemonTypeDao: PokemonTypeDao,
        remoteKeysDao: RemoteKeysDao,
        transactionRunner: TransactionRunner,
        remoteDataSource: RemoteDataSource,
        pokemonApiToPokemonEntityMapper: PokemonApiToPokemonEntityMapper,
        pokemonTypeApiToPokemonTypeEntityMapper: PokemonTypeApiToPokemonTypeEntityMapper,
        dispatcher: CoroutineDispatcher
    ): RemoteMediator<Int, PokemonWithTypes> = PokemonRemoteMediator(
        pokemonDao = pokemonDao,
        pokemonTypeDao = pokemonTypeDao,
        remoteKeysDao = remoteKeysDao,
        transactionRunner = transactionRunner,
        remoteDataSource = remoteDataSource,
        pokemonApiToPokemonEntityMapper = pokemonApiToPokemonEntityMapper,
        pokemonTypeApiToPokemonTypeEntityMapper = pokemonTypeApiToPokemonTypeEntityMapper,
        dispatcher = dispatcher
    )

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    fun providePager(
        pokemonRemoteMediator: PokemonRemoteMediator,
        pokemonDao: PokemonDao,
    ): Pager<Int, PokemonWithTypes> = Pager(
        initialKey = INITIAL_OFFSET,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = pokemonRemoteMediator,
        pagingSourceFactory = { pokemonDao.pagingSource() }
    )
}
