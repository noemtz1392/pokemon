package mx.com.test.android.list.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mx.com.test.android.list.interactor.GetPokemonInfoByIdUseCase
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.interactor.UpdateFavoriteUseCase
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper
import mx.com.test.android.list.screen.PokemonViewModel
import mx.com.test.android.list.screen.info.PokemonInfoViewModel

@Module
@InstallIn(ViewModelComponent::class)
object PokemonListModule {

    @Provides
    @ViewModelScoped
    fun providePokemonListViewModel(
        getPokemonListUseCase: GetPokemonListUseCase,
        pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
        pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
        getPokemonInfoByIdUseCase: GetPokemonInfoByIdUseCase,
        updateFavoriteUseCase: UpdateFavoriteUseCase
    ): PokemonViewModel {
        return PokemonViewModel(
            getPokemonListUseCase = getPokemonListUseCase,
            pokemonToPokemonItemMapper = pokemonToPokemonItemMapper,
            pokemonItemToPokemonMapper = pokemonItemToPokemonMapper,
            getPokemonInfoByIdUseCase = getPokemonInfoByIdUseCase,
            updateFavoriteUseCase = updateFavoriteUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun providePokemonInfoViewModel(
        getPokemonInfoByIdUseCase: GetPokemonInfoByIdUseCase,
        updateFavoriteUseCase: UpdateFavoriteUseCase,
        pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
        pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
        savedStateHandle: SavedStateHandle,
    ): PokemonInfoViewModel {
        return PokemonInfoViewModel(
            getPokemonInfoByIdUseCase = getPokemonInfoByIdUseCase,
            updateFavoriteUseCase = updateFavoriteUseCase,
            pokemonToPokemonItemMapper = pokemonToPokemonItemMapper,
            pokemonItemToPokemonMapper = pokemonItemToPokemonMapper,
            savedStateHandle = savedStateHandle
        )
    }
}