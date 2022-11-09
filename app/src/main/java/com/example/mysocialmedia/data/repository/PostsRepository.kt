package com.example.mysocialmedia.data.repository

import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.model.Posts
import retrofit2.Response

interface PostsRepository {

    suspend fun getCommentsByPost(id: Int): Response<Comments>

    suspend fun getPosts(id: Int): Response<Posts>
}