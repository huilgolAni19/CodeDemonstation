package com.example.mysocialmedia.model

import com.google.gson.annotations.SerializedName

data class PhotosItem(

    @SerializedName("albumId")
    val albumId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String
)