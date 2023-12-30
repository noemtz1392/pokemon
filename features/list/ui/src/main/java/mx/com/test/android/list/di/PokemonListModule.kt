package mx.com.test.android.list.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mx.com.test.android.list.PokemonToPokemonItemMapper
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.screen.PokemonListViewModel

@Module
@InstallIn(ViewModelComponent::class)
object PokemonListModule {

    @Provides
    @ViewModelScoped
    fun providePokemonListViewModel(
        getPokemonListUseCase: GetPokemonListUseCase,
        pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
        savedStateHandle: SavedStateHandle
    ): PokemonListViewModel {
        return PokemonListViewModel(
            getPokemonListUseCase = getPokemonListUseCase,
            pokemonToPokemonItemMapper = pokemonToPokemonItemMapper,
            savedState = savedStateHandle,
        )
    }
}