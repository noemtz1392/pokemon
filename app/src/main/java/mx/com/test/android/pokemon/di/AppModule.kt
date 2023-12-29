package mx.com.test.android.pokemon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.list.di.UseCaseModule
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideExceptionHandle() = ExceptionHandler { throwable ->
        Timber.tag("Exception").w(throwable)
    }
}