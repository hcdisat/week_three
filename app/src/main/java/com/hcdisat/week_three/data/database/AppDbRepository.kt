package com.hcdisat.week_three.data.database

import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.utils.Genre
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AppDbRepository @Inject constructor(
    private var database: MusicTrackDatabase
) : AppDbRepositoryContract {

    /**
     * get all the tracks from the database
     */
    override fun allTracks(genre: Genre): Single<List<MusicTrack>> =
        database.musicTracksDAO().getByGenre(genre)

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work
     * off the main thread.
     * @return Completable (RXJava Completable)
    */
    override fun insert(track: MusicTrack, genre: Genre) = insert(listOf(track), genre)

    /**
     * Inserts many tracks
     */
    override fun insert(tracks: List<MusicTrack>, genre: Genre): Completable {
        setGenre(tracks, genre)

        return database.musicTracksDAO().insertMany(tracks)
    }

    /**
     * deletes all records in the table
     */
    override fun deleteAll(genre: Genre): Completable =
        database.musicTracksDAO().deleteAll(genre)

    /**
     * sets a genre to the passed list
     */
    override fun setGenre(tracks: List<MusicTrack>, genre: Genre) {
        tracks.forEach {
            it.genre = genre
        }
    }
}

interface AppDbRepositoryContract {
    /**
     * get all the tracks from the database
     */
    fun allTracks(genre: Genre): Single<List<MusicTrack>>

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work
     * off the main thread.
     * @return Completable (RXJava Completable)
     */
    fun insert(track: MusicTrack, genre: Genre): Completable

    /**
     * Inserts many tracks
     */
    fun insert(tracks: List<MusicTrack>, genre: Genre): Completable

    /**
     * deletes all records in the table
     */
    fun deleteAll(genre: Genre): Completable

    /**
     * sets a genre to the passed list
     */
    fun setGenre(tracks: List<MusicTrack>, genre: Genre)
}