package com.example.philm.di

import com.example.philm.core.utils.AppConstants
import com.example.philm.data.local.database.PhilmDatabase
import com.example.philm.data.network.NetworkService
import com.example.philm.data.repository.MovieDetailsLocalRepositoryImpl
import com.example.philm.data.repository.MovieRepositoryImpl
import com.example.philm.data.repository.SeriesDetailsLocalRepositoryImpl
import com.example.philm.data.repository.TrendingLocalRepositoryImpl
import com.example.philm.domain.repository.MovieDetailsLocalRepository
import com.example.philm.domain.repository.MovieRepository
import com.example.philm.domain.repository.SeriesDetailsLocalRepository
import com.example.philm.domain.repository.TrendingLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideTheMovieApi(): NetworkService =
        Retrofit
            .Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(provideJson().asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true // Highly recommended
        isLenient = true         // Can be helpful with slightly malformed JSON
        prettyPrint = false      // Good for production (smaller output); true for debugging
        coerceInputValues = true // If an API might send null for a non-nullable field with a default
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: NetworkService,database: PhilmDatabase): MovieRepository =
        MovieRepositoryImpl(movieApi, database)

    @Singleton
    @Provides
    fun provideMovieDetailsLocalRepository(database: PhilmDatabase): MovieDetailsLocalRepository =
        MovieDetailsLocalRepositoryImpl(database)

    @Singleton
    @Provides
    fun provideSeriesDetailsLocalRepository(database: PhilmDatabase): SeriesDetailsLocalRepository =
        SeriesDetailsLocalRepositoryImpl(database)

    @Singleton
    @Provides
    fun provideTrendingLocalRepository(database: PhilmDatabase): TrendingLocalRepository =
        TrendingLocalRepositoryImpl(database)
}