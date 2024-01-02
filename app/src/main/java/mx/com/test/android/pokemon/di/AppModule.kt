package mx.com.test.android.pokemon.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.domain.ExceptionHandler
import mx.com.test.android.list.di.UseCaseModule
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExceptionHandle() = ExceptionHandler { throwable ->
        Timber.tag("Exception").w(throwable)
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.20)
                    .strongReferencesEnabled(true)
                    .build()
            }.diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("pokemon_image_cache"))
                    .maxSizeBytes(5 * 1024 * 1024)
                    .build()

            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}