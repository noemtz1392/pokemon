package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.screen.PokemonListViewModel

@Module
@InstallIn(ViewModelComponent::class)
object PokemonListModule {

    @Provides
    @ViewModelScoped
    fun providePokemonListViewModel(
        getPokemonListUseCase: GetPokemonListUseCase
    ): PokemonListViewModel {
        return PokemonListViewModel(
            getPokemonListUseCase = getPokemonListUseCase
        )
    }
}