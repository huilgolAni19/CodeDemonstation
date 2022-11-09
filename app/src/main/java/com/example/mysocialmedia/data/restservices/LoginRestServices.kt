package com.example.mysocialmedia.data.restservices

import com.example.mysocialmedia.model.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginRestServices {

    @GET("/users")
    suspend fun signInWithEmailAndPassword(@Query("email") email: String): Response<UserData>

}