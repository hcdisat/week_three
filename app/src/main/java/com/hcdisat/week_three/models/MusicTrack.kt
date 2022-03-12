package com.hcdisat.week_three.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "music_tracks")
data class MusicTrack(
    @SerializedName("trackId")
    @PrimaryKey @ColumnInfo(name = "track_id") val trackId: Int,

    @SerializedName("artistName")
    @ColumnInfo(name = "artist_name")val artistName: String,

    @SerializedName("collectionName")
    @ColumnInfo(name = "collection_name") val collectionName: String,

    @SerializedName("artworkUrl60")
    @ColumnInfo(name = "artwork_url") val artworkUrl: String,

    @SerializedName("trackNumber")
    @ColumnInfo(name = "track_number") val trackNumber: Int
)