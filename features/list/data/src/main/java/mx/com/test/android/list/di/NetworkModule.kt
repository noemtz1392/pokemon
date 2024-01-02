package mx.com.test.android.list.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.test.android.list.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT = 1L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("CALL-API").i("********************")
            Timber.tag("CALL-API").i(message)
            Timber.tag("CALL-API").i("********************")
        }.run {
            level = HttpLoggingInterceptor.Level.BODY
            this
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    @Singleton
    @Provides
    fun provideFlightApi(okHttpClient: OkHttpClient, moshi: Moshi): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}