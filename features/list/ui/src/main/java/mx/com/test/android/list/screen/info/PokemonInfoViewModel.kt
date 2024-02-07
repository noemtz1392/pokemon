package mx.com.test.android.list.screen.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import mx.com.test.android.domain.common.Result
import mx.com.test.android.domain.common.toResult
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.interactor.GetPokemonInfoByIdUseCase
import mx.com.test.android.list.interactor.UpdateFavoriteUseCase
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    getPokemonInfoByIdUseCase: GetPokemonInfoByIdUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
    private val pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id by lazy { savedStateHandle["id"] ?: 0 }

    val uiState = getPokemonInfoByIdUseCase(id).toResult()
        .onStart { PokemonInfoState(isLoading = true) }
        .map { result ->
            when (result) {
                is Result.Failure -> TODO()
                is Result.Success -> PokemonInfoState(
                    pokemonToPokemonItemMapper.mapFrom(result.data!!),
                    isLoading = false
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = PokemonInfoState()
        )

    fun updateFavorite(pokemon: PokemonItem) {

        updateFavoriteUseCase(pokemon.id, pokemon.isFavorite)
            .toResult()
            .map {
                when (it) {
                    is Result.Failure -> Timber.e(it.exception)
                    is Result.Success -> Timber.i(it.data.toString())
                }
            }
            .onStart {
                Timber.i("onStart")
            }
            .catch {
                Timber.e(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PokemonInfoState()
            )
    }
}