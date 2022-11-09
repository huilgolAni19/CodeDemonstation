package com.example.mysocialmedia.data.restservices

import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsRestServices {

    @GET("users/{id}/posts")
    suspend fun getPosts(@Path("id") id: Int): Response<Posts>

    @GET("/posts/{id}/comments")
    suspend fun getCommentsByPost(@Path("id") id: Int): Response<Comments>
}