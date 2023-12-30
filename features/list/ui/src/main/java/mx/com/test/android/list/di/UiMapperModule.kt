package mx.com.test.android.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.list.PokemonToPokemonItemMapper

@Module
@InstallIn(SingletonComponent::class)
object UiMapperModule {

    @Provides
    fun providePokemonToPokemonItemMapper(): PokemonToPokemonItemMapper {
        return PokemonToPokemonItemMapper()
    }
}