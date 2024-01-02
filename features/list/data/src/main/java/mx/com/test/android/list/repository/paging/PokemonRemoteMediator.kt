package mx.com.test.android.list.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.com.test.android.data.dao.TransactionRunner
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.RemoteKeysEntity
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.source.remote.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val transactionRunner: TransactionRunner,
    private val remoteDataSource: RemoteDataSource,
    private val pokemonApiToPokemonEntityMapper: PokemonApiToPokemonEntityMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES)
        return withContext(dispatcher) {
            if (isExpiredCache(cacheTimeout)) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                1
            }

            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                } else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }
        return try {
            val pokemonList = remoteDataSource.getPokemonList(
                (page - 1) * state.config.pageSize,
                state.config.pageSize
            )
            val endOfPagination: Boolean = when (pokemonList) {
                is Result.Failure -> return MediatorResult.Error(pokemonList.exception!!)
                is Result.Success -> pokemonList.data.isEmpty()
            }

            if (pokemonList.data.isNotEmpty()) {
                val previousKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPagination) null else page + 1
                val remoteKeys = pokemonList.data.map {
                    RemoteKeysEntity(
                        id = it.id,
                        previousKey = previousKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }


                transactionRunner {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeysDao.deleteAll()
                        pokemonDao.deleteAll()
                    }
                    remoteKeysDao.insertAll(remoteKeys)
                    pokemonDao.insertAll(pokemonApiToPokemonEntityMapper.mapFromList(pokemonList.data))
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }

    private suspend fun isExpiredCache(cacheTimeout: Long): Boolean {
        return System.currentTimeMillis() - (remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout
    }

    private suspend fun getRemoteKeyForFirstItem(): RemoteKeysEntity? {
        return withContext(dispatcher) {
            remoteKeysDao.getRemoteKeys().firstOrNull()
        }
    }

    private suspend fun getRemoteKeyForLastItem(): RemoteKeysEntity? {
        return withContext(dispatcher) {
            remoteKeysDao.getRemoteKeys().lastOrNull()
        }
    }
}