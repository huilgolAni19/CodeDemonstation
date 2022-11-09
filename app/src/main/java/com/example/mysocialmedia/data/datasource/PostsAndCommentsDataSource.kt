package com.example.mysocialmedia.data.datasource

import com.example.mysocialmedia.data.repository.PostsRepository
import com.example.mysocialmedia.data.restservices.PostsRestServices
import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.model.Posts
import retrofit2.Response
import javax.inject.Inject

class PostsAndCommentsDataSource(private val postsRestServices: PostsRestServices): PostsRepository {

    override suspend fun getCommentsByPost(id: Int): Response<Comments> {
        return  postsRestServices.getCommentsByPost(id)
    }

    override suspend fun getPosts(id: Int): Response<Posts> {
        return postsRestServices.getPosts(id)
    }
}