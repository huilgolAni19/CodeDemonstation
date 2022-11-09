package com.example.mysocialmedia.data.restservices

import com.example.mysocialmedia.model.Todos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TodosRestService {

    @GET("users/{id}/todos")
    suspend fun getTodoItemsByUserId(@Path("id") id: Int): Response<Todos>

    @GET("users/{id}/todos")
    suspend fun getTodoItemByCompletionStatus(@Path("id") id: Int, @Query("completed")  completed: Boolean): Response<Todos>

}