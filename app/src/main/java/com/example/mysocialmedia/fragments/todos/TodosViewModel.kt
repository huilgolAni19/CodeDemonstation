package com.example.mysocialmedia.fragments.todos

import androidx.lifecycle.*
import com.example.mysocialmedia.R
import com.example.mysocialmedia.data.datasource.TodoDataSource
import com.example.mysocialmedia.model.Todos
import com.example.mysocialmedia.utils.NetworkStatus
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

class TodosViewModel(private val todoDataSource: TodoDataSource) : ViewModel() {

    private val id: Int = todoDataSource.userId


    val todoFilter: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = R.id.radio_all
    }

    private val _todoStatus: MutableSharedFlow<Int> = MutableSharedFlow()

    private var _status: MutableSharedFlow<NetworkStatus> = MutableSharedFlow()


    val status: SharedFlow<NetworkStatus> = _status.asSharedFlow()



    val todos: LiveData<Todos> = liveData {

        _status.emit(NetworkStatus.REQUEST_SENT)

        val response: Response<Todos> = todoDataSource.getTodoItemsByUserId(id)
        val todosData: Todos? = response.body()

        _status.emit(NetworkStatus.RESPONSE_RECEIVED)

        if(todoFilter.value!! == R.id.radio_all) {
            todosData?.let { data ->
                emit(data)
            }
        }

        _todoStatus.collectLatest { currentValue: Int ->

            when (currentValue) {

                R.id.radio_all -> {
                    todosData?.let { data ->
                        emit(data)
                    }
                }

                R.id.radio_completed -> {
                    todosData?.let { data ->
                        val completedData = data.filter { todosItem -> todosItem.completed }
                        val gson = Gson()
                        val json = gson.toJson(completedData)
                        val completedTodos: Todos = gson.fromJson(json, Todos::class.java)
                        emit(completedTodos)
                    }
                }

                R.id.radio_not_completed -> {
                    todosData?.let { data ->
                        val notCompletedData = data.filter { todosItem -> !todosItem.completed }
                        val gson = Gson()
                        val json = gson.toJson(notCompletedData)
                        val notCompletedTodos: Todos = gson.fromJson(json, Todos::class.java)
                        emit(notCompletedTodos)
                    }
                }
            }
        }
    }


    fun onChanged() {
        viewModelScope.launch(Main) {
            _todoStatus.emit(todoFilter.value!!)
        }
    }
}