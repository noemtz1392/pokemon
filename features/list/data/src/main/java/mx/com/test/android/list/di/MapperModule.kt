package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.mapper.PokemonEntityToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonEntityMapper
import mx.com.test.android.list.mapper.PokemonTypeApiToPokemonTypeEntityMapper
import mx.com.test.android.list.mapper.PokemonTypeEntityToTypeListMapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providePokemonApiToPokemonEntityMapper(): PokemonApiToPokemonEntityMapper =
        PokemonApiToPokemonEntityMapper()

    @Provides
    fun providePokemonEntityToPokemonMapper(): PokemonEntityToPokemonMapper =
        PokemonEntityToPokemonMapper()

    @Provides
    fun providePokemonToPokemonEntityMapper(): PokemonToPokemonEntityMapper =
        PokemonToPokemonEntityMapper()

    @Provides
    fun providePokemonTypeApiToPokemonTypeEntityMapper(): PokemonTypeApiToPokemonTypeEntityMapper =
        PokemonTypeApiToPokemonTypeEntityMapper()

    @Provides
    fun providePokemonTypeEntityToTypeListMapper(): PokemonTypeEntityToTypeListMapper =
        PokemonTypeEntityToTypeListMapper()
}