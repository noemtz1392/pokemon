package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.list.mapper.PokemonItemToPokemonMapper
import mx.com.test.android.list.mapper.PokemonToPokemonItemMapper

@Module
@InstallIn(SingletonComponent::class)
object UiMapperModule {

    @Provides
    fun providePokemonToPokemonItemMapper(): PokemonToPokemonItemMapper {
        return PokemonToPokemonItemMapper()
    }

    @Provides
    fun providePokemonItemToPokemonMapper(): PokemonItemToPokemonMapper{
        return PokemonItemToPokemonMapper()
    }
}