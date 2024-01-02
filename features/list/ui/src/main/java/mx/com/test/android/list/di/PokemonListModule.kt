package mx.com.test.android.list.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mx.com.test.android.list.interactor.AddToFavoriteUseCase
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.interactor.RemoveToFavoriteUseCase
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper
import mx.com.test.android.list.screen.PokemonListViewModel

@Module
@InstallIn(ViewModelComponent::class)
object PokemonListModule {

    @Provides
    @ViewModelScoped
    fun providePokemonListViewModel(
        getPokemonListUseCase: GetPokemonListUseCase,
        addToFavoriteUseCase: AddToFavoriteUseCase,
        removeToFavoriteUseCase: RemoveToFavoriteUseCase,
        pokemonToPokemonItemMapper: PokemonToPokemonItemMapper,
        pokemonItemToPokemonMapper: PokemonItemToPokemonMapper,
        savedStateHandle: SavedStateHandle
    ): PokemonListViewModel {
        return PokemonListViewModel(
            getPokemonListUseCase = getPokemonListUseCase,
            addToFavoriteUseCase = addToFavoriteUseCase,
            removeToFavoriteUseCase = removeToFavoriteUseCase,
            pokemonToPokemonItemMapper = pokemonToPokemonItemMapper,
            pokemonItemToPokemonMapper = pokemonItemToPokemonMapper,
            savedState = savedStateHandle,
        )
    }
}