package com.hcdisat.week_three.models


import com.google.gson.annotations.SerializedName

data class MusicTrack(
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("collectionName")
    val collectionName: String,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String,
    @SerializedName("trackNumber")
    val trackNumber: Int
)