package com.hcdisat.week_three.data.database

import androidx.room.*
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.utils.Genre
import io.reactivex.Completable
import io.reactivex.Single

/**
 * [MusicTracksDAO] Data access object used fro database read/write operations
 */
@Dao
interface MusicTracksDAO {

    /**
     * gets all records in music_tracks table
     */
    @Query("SELECT * FROM music_tracks")
    fun getAll(): Single<List<MusicTrack>>

    /**
     * selects all [MusicTrack] objects with matching genre
     */
    @Query("SELECT * FROM music_tracks WHERE genre = :genre")
    fun getByGenre(genre: Genre): Single<List<MusicTrack>>

    /**
     * inserts a list of tracks into music_tracks table
     * if an existing column is found within the list is going to be
     * ignored by the [OnConflictStrategy] defined below
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMany(tracks: List<MusicTrack>): Completable

    /**
     * deletes all tracks from table
     */
    @Query("DELETE FROM music_tracks WHERE genre = :genre")
    fun deleteAll(genre: Genre): Completable
}