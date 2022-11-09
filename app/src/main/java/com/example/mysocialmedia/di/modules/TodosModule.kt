package com.example.mysocialmedia.di.modules

import com.example.mysocialmedia.data.datasource.TodoDataSource
import com.example.mysocialmedia.data.restservices.TodosRestService
import com.example.mysocialmedia.utils.SessionController
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class TodosModule() {

    @Singleton
    @Provides
    fun provideTodosRestService(retrofit: Retrofit): TodosRestService {
        return retrofit.create(TodosRestService::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoDatasource(
        todosRestService: TodosRestService,
        sessionController: SessionController
    ): TodoDataSource {
        return TodoDataSource(todosRestService, sessionController)
    }
}