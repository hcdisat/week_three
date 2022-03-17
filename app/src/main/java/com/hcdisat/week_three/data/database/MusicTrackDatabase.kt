package com.hcdisat.week_three.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hcdisat.week_three.models.MusicTrack

const val DB_VERSION = 1
const val DB_NAME = "music_tracks_database"

@Database(entities = [MusicTrack::class], version = DB_VERSION)
abstract class MusicTrackDatabase : RoomDatabase() {

    abstract fun musicTracksDAO(): MusicTracksDAO

    companion object {

        /**
         * Singleton prevents multiple instances
         * of database opening at the same time.
         */
        @Volatile
        private var INSTANCE : MusicTrackDatabase? = null

        /**
         * checks if instance is not null and return it
         * if is null build an instance, assigns it and then return it
         */
        fun getInstance(context: Context): MusicTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also {
                    INSTANCE = it
                }
            }
        }

        /**
         * Builds Room database
         */
        private fun build(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MusicTrackDatabase::class.java, DB_NAME).build()
    }
}