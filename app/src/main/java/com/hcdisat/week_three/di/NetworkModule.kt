package com.hcdisat.week_three.di

import com.google.gson.Gson
import com.hcdisat.week_three.data.network.MusicApiRepository
import com.hcdisat.week_three.data.network.MusicApiRepositoryContract
import com.hcdisat.week_three.data.network.MusicTracksAPI
import com.hcdisat.week_three.data.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** OkHttpClient requests timeout */
private const val TIMEOUT = 30L

@Module
class NetworkModule  {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun requestInterceptor(): RequestInterceptor = RequestInterceptor()

    /**
     * [HttpLoggingInterceptor] interceptor used to log HTTP calls to logcat
     */
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * [OkHttpClient] Http client used to intercept request & responses
     * and to make API calls using HTTP
     */
    @Provides
    fun providesProvidesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

    /**
     * [Retrofit] Service wrapped with [MusicTracksAPI]
     */
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): MusicTracksAPI  =
        Retrofit.Builder()
            .baseUrl(MusicTracksAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MusicTracksAPI::class.java)

    /**
     * APi repository
     */
    @Provides
    fun providesMusicApiRepository(
        musicTracksAPI: MusicTracksAPI
    ): MusicApiRepositoryContract = MusicApiRepository(musicTracksAPI)
}