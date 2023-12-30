package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.list.mapper.PokemonApiToPokemonEntityMapper
import mx.com.test.android.list.mapper.PokemonEntityToPokemonMapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providePokemonApiToPokemonEntityMapper() = PokemonApiToPokemonEntityMapper()

    @Provides
    fun providePokemonEntityToPokemonMapper() = PokemonEntityToPokemonMapper()
}