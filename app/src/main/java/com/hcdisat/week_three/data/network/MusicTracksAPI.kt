package com.hcdisat.week_three.data.network

import com.hcdisat.week_three.models.GenreSummary
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface MusicTracksAPI {

    @GET(ROCK_URL)
    fun getRockTracks(): Single<GenreSummary>

    @GET(POP_URL)
    fun getPopTracks(): Single<GenreSummary>

    @GET(CLASSIC_URL)
    fun getClassicTracks(): Single<GenreSummary>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/search/"
        const val CLASSIC_URL = "?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
        const val ROCK_URL = "?term=rock&amp;media=music&amp;entity=song&amp;limit=50"
        const val POP_URL = "?term=pop&amp;media=music&amp;entity=song&amp;limit=50"
    }
}