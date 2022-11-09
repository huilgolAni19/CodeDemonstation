package com.example.mysocialmedia.model

import com.google.gson.annotations.SerializedName

data class TodosItem(

    @SerializedName("completed")
    val completed: Boolean,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("userId")
    val userId: Int
)