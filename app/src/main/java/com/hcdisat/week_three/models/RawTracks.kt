package com.hcdisat.week_three.models


import com.google.gson.annotations.SerializedName

data class RawTracks(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
)