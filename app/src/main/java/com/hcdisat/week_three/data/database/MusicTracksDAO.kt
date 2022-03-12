package com.hcdisat.week_three.data.database

import androidx.room.*
import com.hcdisat.week_three.models.MusicTrack
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
     * selects a [MusicTrack] object with matching trackId
     */
    @Query("SELECT * FROM music_tracks WHERE track_id = :trackId")
    fun getByTrackId(trackId: Int): Single<MusicTrack?>

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
    @Query("DELETE FROM music_tracks")
    fun deleteAll(): Completable
}