package com.example.mysocialmedia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.TodosLayoutBinding
import com.example.mysocialmedia.model.Todos
import com.example.mysocialmedia.model.TodosItem

class TodosAdapter(private val todos: Todos): Adapter<TodosAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val binding: TodosLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.todos_layout, parent, false)
        return TodosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        val item: TodosItem = todos[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun setTodos(todos: Todos) {
        this.todos.clear()
        this.todos.addAll(todos)
        this.notifyDataSetChanged()
    }
    class TodosViewHolder(private val binding: TodosLayoutBinding): ViewHolder(binding.root) {

        fun bind(todosItem: TodosItem) {
            binding.todosItem = todosItem
        }
    }
}