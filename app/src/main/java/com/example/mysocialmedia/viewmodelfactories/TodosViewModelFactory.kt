package com.example.mysocialmedia.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysocialmedia.data.datasource.TodoDataSource
import com.example.mysocialmedia.fragments.todos.TodosViewModel

@Suppress("UNCHECKED_CAST")
class TodosViewModelFactory(private val todoDataSource: TodoDataSource): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodosViewModel::class.java)) {
            return TodosViewModel(todoDataSource) as T
        }
        return super.create(modelClass)
    }

}