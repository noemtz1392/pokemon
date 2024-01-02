package mx.com.test.android.list.screen

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.launch
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.interactor.AddToFavoriteUseCase
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.interactor.RemoveToFavoriteUseCase
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeToFavoriteUseCase: RemoveToFavoriteUseCase,
    private val pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
    private val pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
    savedState: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<PokemonItem>>(PagingData.empty())
    val uiState: StateFlow<PagingData<PokemonItem>> = _uiState.asStateFlow()

    private var currentPage: Int = savedState.get<Int>(KEY_CURRENT_PAGE) ?: 0

    init {
        onEvent(PokemonListEvent.GetPokemonList)
    }

    fun onEvent(event: PokemonListEvent) {
        viewModelScope.launch {
            when (event) {
                PokemonListEvent.GetPokemonList -> getPokemonList()
                is PokemonListEvent.AddToFavorites -> addToFavorite(event.pokemon)
                is PokemonListEvent.RemoveToFavorites -> removeToFavorite(event.pokemon)
            }
        }
    }

    private suspend fun getPokemonList() {
        getPokemonListUseCase(currentPage * PAGE_SIZE, PAGE_SIZE)
            .distinctUntilChanged()
            .map { pagingData ->
                pagingData.map { pokemon ->
                    pokemonToPokemonItemMapper.mapFrom(pokemon)
                }
            }
            .cachedIn(viewModelScope)
            .collect { _uiState.value = it }
    }

    private suspend fun addToFavorite(pokemon: PokemonItem) {
        addToFavoriteUseCase(pokemonItemToPokemonMapper.mapFrom(pokemon))
    }

    private suspend fun removeToFavorite(pokemon: PokemonItem) {
        removeToFavoriteUseCase(pokemonItemToPokemonMapper.mapFrom(pokemon))
    }

    companion object {
        const val KEY_CURRENT_PAGE = "KEY_STATE_CURRENT_PAGE"
        const val PAGE_SIZE = 25
    }
}