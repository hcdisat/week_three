package com.hcdisat.week_three.models


import com.google.gson.annotations.SerializedName

data class GenreSummary(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val musicTracks: List<MusicTrack>
)