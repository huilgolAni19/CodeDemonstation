package com.example.mysocialmedia.data.datasource

import com.example.mysocialmedia.data.repository.TodosRepository
import com.example.mysocialmedia.data.restservices.TodosRestService
import com.example.mysocialmedia.model.Todos
import com.example.mysocialmedia.utils.SessionController
import retrofit2.Response

class TodoDataSource(
    private val restService: TodosRestService,
    private val sessionController: SessionController
): TodosRepository {

    val userId: Int get() = sessionController.userDetails!!.id

    override suspend fun getTodoItemsByUserId(
        id: Int
    ): Response<Todos> {
        return restService.getTodoItemsByUserId(id)
    }

    override suspend fun getTodoItemByCompletionStatus(
        id: Int,
        completed: Boolean,
    ): Response<Todos> {
        return restService.getTodoItemByCompletionStatus(id, completed)
    }

}