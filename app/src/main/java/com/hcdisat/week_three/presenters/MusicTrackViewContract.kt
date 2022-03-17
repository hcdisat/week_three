package com.hcdisat.week_three.presenters

import com.hcdisat.week_three.models.MusicTrack

/**
 * Contract must be implemented by an Activity or
 * Fragment in order to communicate with the presenter
 */
interface MusicTrackViewContract {
    /**
     * loading data from repository
     */
    fun isLoading(loading: Boolean)

    /**
     * gets all tracks from repository
     */
    fun success(tracks: List<MusicTrack>): MusicTrackViewContract

    /**
     * get any error produced in the observable
     */
    fun error(throwable: Throwable): MusicTrackViewContract
}