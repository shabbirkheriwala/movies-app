package com.ssk.movies.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssk.movies.BuildConfig
import com.ssk.movies.model.local.AppDatabase
import com.ssk.movies.model.remote.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* DI with Hilt.
* This class will provide instance of Retrofit, service, repository, database & dao
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*
    * The api calls for the app :-
    * https://api.themoviedb.org/3/movie/popular?api_key=9d8cc8e620ec4bcbcb682f8f2cca8285&language=en-US&page=1
    * https://api.themoviedb.org/3/movie/550988?api_key=9d8cc8e620ec4bcbcb682f8f2cca8285&language=en-US
    * https://image.tmdb.org/t/p/w185/hEqw9swA8gFJuNjgWYEypwZfkZg.jpg
    * */

    val BASE_URL = "https://api.themoviedb.org/3/"
    val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185/"

    // Gson builder for the Retrofit
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /*
    * The retrofit instance
    * */
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        // logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // interceptor to add the api key along with the request
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val client = OkHttpClient.Builder()

        // API logging only on Debug builds
        if (BuildConfig.DEBUG) {
            client.addInterceptor(loggingInterceptor)
        }

        client.addInterceptor(requestInterceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /*
    * Provides the DB instance
    * */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) = db.moviesDao()

    @Singleton
    @Provides
    fun provideMovieDetailsDao(db: AppDatabase) = db.movieDetailsDao()

    @Singleton
    @Provides
    fun providePopularMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)
}
