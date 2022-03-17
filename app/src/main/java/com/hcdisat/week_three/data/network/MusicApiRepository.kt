package com.hcdisat.week_three.data.network

import com.hcdisat.week_three.models.GenreSummary
import io.reactivex.Single
import javax.inject.Inject

/**
 * All these methods returns [Single<GenreSummary>] list of observables
 */
class MusicApiRepository @Inject constructor(
    private val musicTracksAPI: MusicTracksAPI
): MusicApiRepositoryContract {

    override fun getPop(): Single<GenreSummary> = musicTracksAPI.getPopTracks()

    override fun getRock(): Single<GenreSummary> = musicTracksAPI.getRockTracks()

    override fun getClassic(): Single<GenreSummary> = musicTracksAPI.getClassicTracks()
}

/**
 * all calls
 * @returns [Single] of GenreSummary
 */
interface MusicApiRepositoryContract {

    fun getPop(): Single<GenreSummary>

    fun getRock(): Single<GenreSummary>

    fun getClassic(): Single<GenreSummary>
}