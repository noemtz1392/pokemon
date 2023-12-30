package mx.com.test.android.list.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mx.com.test.android.data.dao.TransactionRunner
import mx.com.test.android.domain.common.Result
import mx.com.test.android.list.db.dao.PokemonDao
import mx.com.test.android.list.db.dao.RemoteKeysDao
import mx.com.test.android.list.db.entities.PokemonEntity
import mx.com.test.android.list.db.entities.RemoteKeysEntity
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.source.remote.PokemonRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val transactionRunner: TransactionRunner,
    private val remoteDataSource: PokemonRemoteDataSource,
    private val pokemonApiToPokemonEntityMapper: PokemonApiToPokemonEntityMapper,
    private val dispatcher: CoroutineDispatcher,
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(60, TimeUnit.MINUTES)
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
            REFRESH -> {
                val remoteKeys = getRemoteKeyForFirstItem()
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem()
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }
        return withContext(dispatcher) {
            try {
                val pokemonList = remoteDataSource.getPokemonList(offset = 0, limit = 25)
                val endOfPagination: Boolean = when (pokemonList) {
                    is Result.Failure -> return@withContext MediatorResult.Error(pokemonList.exception!!)
                    is Result.Success -> pokemonList.data.isEmpty()
                }

                if (pokemonList.data.isNotEmpty()) {
                    if (loadType == REFRESH) {
                        transactionRunner {
                            remoteKeysDao.deleteRemoteKeys()
                            pokemonDao.deleteAll()
                        }
                    }
                    val previousKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPagination) null else page + 1
                    /*val remoteKeys = pokemonList.data.map {
                        RemoteKeysEntity(
                            id = it.id,
                            previousKey = previousKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }*/
                    /*pokemonList.data.onEach {pokemonApi->

                        val pokemonInfo= remoteDataSource.getPokemonInfo(pokemonApi)
                    }*/

                    transactionRunner {
                        //remoteKeysDao.insertAll(remoteKeys)
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
    }

    private suspend fun isExpiredCache(cacheTimeout: Long): Boolean {
        return true
        //return System.currentTimeMillis() - (remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout
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