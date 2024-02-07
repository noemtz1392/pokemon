package mx.com.test.android.list.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.com.test.android.domain.common.Result
import mx.com.test.android.domain.common.toResult
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.interactor.GetPokemonInfoByIdUseCase
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.interactor.UpdateFavoriteUseCase
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper
import mx.com.test.android.list.screen.info.PokemonInfoState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
    private val pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
    private val getPokemonInfoByIdUseCase: GetPokemonInfoByIdUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<PokemonItem>>(PagingData.empty())
    val uiState: StateFlow<PagingData<PokemonItem>> = _uiState.asStateFlow()

    private val _pokemonInfoState = MutableStateFlow(PokemonInfoState())

    init {
        onEvent(PokemonEvent.GetPokemonList)
    }

    fun onEvent(event: PokemonEvent) {
        viewModelScope.launch {
            when (event) {
                PokemonEvent.GetPokemonList -> getPokemonList()
                is PokemonEvent.GetPokemonInfo -> getPokemonInfo(event.id)
                is PokemonEvent.AddToFavorites -> addToFavorite(event.pokemon)
                is PokemonEvent.RemoveToFavorites -> removeToFavorite(event.pokemon)
                is PokemonEvent.UpdateFavorite -> updateFavorite(event.pokemon)
            }
        }
    }

    private suspend fun getPokemonList() {
        getPokemonListUseCase(Unit)
            .distinctUntilChanged()
            .map { pagingData ->
                pagingData.map { pokemon ->
                    pokemonToPokemonItemMapper.mapFrom(pokemon)
                }
            }
            .cachedIn(viewModelScope)
            .collect { _uiState.value = it }
    }

    private fun getPokemonInfo(id: Int) {
        getPokemonInfoByIdUseCase(id).toResult()
            .map { result ->
                when (result) {
                    is Result.Failure -> Timber.e(result.exception)
                    is Result.Success -> _pokemonInfoState.update {
                        it.copy(pokemonItem = pokemonToPokemonItemMapper.mapFrom(result.data!!))
                    }
                }
            }
    }

    private suspend fun addToFavorite(pokemon: PokemonItem) {
    }

    private suspend fun removeToFavorite(pokemon: PokemonItem) {
    }

    private fun updateFavorite(pokemonItem: PokemonItem) {
        /*val pokemon = pokemonItemToPokemonMapper.mapFrom(pokemonItem)
        updateFavoriteUseCase(pokemon = pokemon).toResult()
            .map {
                Timber.d(it.toString())
            }.onStart {
                Timber.d("onStart")
            }.catch {
                Timber.e(it)
            }*/
    }
}