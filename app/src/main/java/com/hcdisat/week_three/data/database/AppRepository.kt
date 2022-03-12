package com.hcdisat.week_three.data.database

import androidx.annotation.WorkerThread
import com.hcdisat.week_three.models.MusicTrack

class AppRepository(private val musicTrackDao: MusicTracksDAO) {

    /**
     * get all the tracks from the database
     * TODO: make it observable
     */
//    fun allTracks(): List<MusicTrack> = musicTrackDao.getAll()

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work
     * off the main thread.
     * @return Completable (RXjava Completable)
    */
    fun insert(track: MusicTrack) =
        musicTrackDao.insertMany(listOf(track))
}