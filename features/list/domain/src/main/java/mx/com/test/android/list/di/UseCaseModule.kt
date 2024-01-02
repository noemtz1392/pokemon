package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.list.interactor.AddToFavoriteUseCase
import mx.com.test.android.list.interactor.GetPokemonInfoById
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import mx.com.test.android.list.interactor.RemoveToFavoriteUseCase
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

    @Provides
    fun provideAddToFavoriteUseCase(
        pokemonRepository: PokemonRepository,
        exceptionHandler: ExceptionHandler,
    ) = AddToFavoriteUseCase(
        pokemonRepository = pokemonRepository,
        exceptionHandler = exceptionHandler,
        dispatcher = Dispatchers.IO,
    )

    @Provides
    fun provideRemoveToFavoriteUseCase(
        pokemonRepository: PokemonRepository,
        exceptionHandler: ExceptionHandler,
    ) = RemoveToFavoriteUseCase(
        pokemonRepository = pokemonRepository,
        exceptionHandler = exceptionHandler,
        dispatcher = Dispatchers.IO,
    )

    @Provides
    fun provideGetPokemonInfoById(
        pokemonRepository: PokemonRepository,
        exceptionHandler: ExceptionHandler,
    ) = GetPokemonInfoById(
        pokemonRepository = pokemonRepository,
        exceptionHandler = exceptionHandler,
        dispatcher = Dispatchers.IO,
    )
}