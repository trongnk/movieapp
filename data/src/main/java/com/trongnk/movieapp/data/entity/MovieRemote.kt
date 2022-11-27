package com.trongnk.movieapp.data.entity

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class MovieRemote(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("duration") val duration: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("trailerLink") val trailerLink: String,
    @SerializedName("poster") val poster: String,
    @SerializedName("watchList") val watchList: Boolean
)
