package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.repository.PokemonRepository

@Module
object UseCaseModule {
    @Provides
    fun provideGetPokemonListUseCase(
        pokemonRepository: PokemonRepository,
        exceptionHandler: ExceptionHandler,
    ) = GetPokemonListUseCase(
        pokemonRepository = pokemonRepository,
        exceptionHandler = exceptionHandler,
        dispatcher = Dispatchers.IO,
    )
}