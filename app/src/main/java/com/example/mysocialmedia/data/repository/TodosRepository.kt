package com.example.mysocialmedia.data.repository

import com.example.mysocialmedia.model.Todos
import retrofit2.Response

interface TodosRepository {

    suspend fun getTodoItemsByUserId(id: Int): Response<Todos>

    suspend fun getTodoItemByCompletionStatus(id: Int, completed: Boolean): Response<Todos>
}