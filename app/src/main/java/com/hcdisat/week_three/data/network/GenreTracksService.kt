package com.hcdisat.week_three.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * [GenreTracksService] [Retrofit] service to interact with the iTunes API
 */
object GenreTracksService {
    private const val TIMEOUT = 30L

    /**
     * [HttpLoggingInterceptor] interceptor used to log HTTP calls to logcat
     */
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * [OkHttpClient] Http client used to intercept request & responses
     * and to make API calls using HTTP
     */
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     * [Retrofit] Service wrapped with [MusicTracksAPI]
     */
    val retrofit: MusicTracksAPI = Retrofit.Builder()
        .baseUrl(MusicTracksAPI.BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
//             .addCallAdapterFactory()
         .client(okHttpClient)
         .build()
         .create(MusicTracksAPI::class.java)
}