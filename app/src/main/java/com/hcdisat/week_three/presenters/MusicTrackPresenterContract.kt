package com.hcdisat.week_three.presenters

import com.hcdisat.week_three.utils.Genre

/**
 * Contract to be implemented by the view using this presenter
 */
interface MusicTrackPresenterContract {

    /**
     * gets tracks from network or db
     * this method works in the background
     */
    fun loadTracks(genre: Genre)

    /**
     * keeps an eye on the network
     * used to react when connection is lost
     */
    fun runNetworkMonitor()
}