package com.hcdisat.week_three.models


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hcdisat.week_three.utils.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "music_tracks")
data class MusicTrack(
    @SerializedName("trackId")
    @PrimaryKey @ColumnInfo(name = "track_id") val trackId: Int,

    @SerializedName("trackName")
    @ColumnInfo(name = "track_name") val trackName: String,

    @SerializedName("previewUrl")
    @ColumnInfo(name = "preview_url") val previewUrl: String,

    @SerializedName("trackNumber")
    @ColumnInfo(name = "track_number") val trackNumber: Int,

    @SerializedName("trackPrice")
    @ColumnInfo(name = "track_price") val trackPrice: Double,

    @SerializedName("artistName")
    @ColumnInfo(name = "artist_name")val artistName: String,

    @SerializedName("collectionName")
    @ColumnInfo(name = "collection_name") val collectionName: String,

    @SerializedName("artworkUrl60")
    @ColumnInfo(name = "artwork_url") val artworkUrl: String,

    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "genre") var genre: Genre,
): Parcelable