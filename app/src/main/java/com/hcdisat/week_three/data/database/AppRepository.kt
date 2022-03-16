package com.hcdisat.week_three.data.database

import android.content.Context
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.utils.Genre
import io.reactivex.Completable
import io.reactivex.Single

class AppRepository(
    context: Context? = null,
    private var database: MusicTrackDatabase? = MusicTrackDatabase.getInstance(context!!)
) {

    /**
     * get all the tracks from the database
     */
    fun allTracks(genre: Genre): Single<List<MusicTrack>> =
        database?.musicTracksDAO()!!.getByGenre(genre)

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work
     * off the main thread.
     * @return Completable (RXJava Completable)
    */
    fun insert(track: MusicTrack, genre: Genre) = insert(listOf(track), genre)

    /**
     * Inserts many tracks
     */
    fun insert(tracks: List<MusicTrack>, genre: Genre): Completable {
        setGenre(tracks, genre)

        return database?.musicTracksDAO()!!.insertMany(tracks)
    }


    /**
     * deletes all records in the table
     */
    fun deleteAll(genre: Genre): Completable =
        database?.musicTracksDAO()!!.deleteAll(genre)

    /**
     * sets a genre to the passed list
     */
    private fun setGenre(tracks: List<MusicTrack>, genre: Genre) {
        tracks.forEach {
            it.genre = genre
        }
    }

    /**
     * destroys repository
     */
    fun destroy() {
        database = null
    }
}