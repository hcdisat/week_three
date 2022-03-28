package com.hcdisat.week_three.data.network

import com.hcdisat.week_three.models.GenreSummary
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicTracksAPI {

    @GET(PATH)
    fun getTracks(
        @Query("term") genre: String,
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50,
    ): Single<GenreSummary>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
        private const val PATH = "search"
    }
}