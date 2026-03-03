package com.nithesh.roomdatabase_compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nithesh.roomdatabase_compose.data.Todo
import com.nithesh.roomdatabase_compose.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository): ViewModel() {
    val todos: StateFlow<List<Todo>> = repository.allTodos.map{
        it.sortedByDescending { todo -> todo.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(title: String) = viewModelScope.launch {
        repository.addTodo(Todo(title = title))

    }
    fun deleteTodoById(todo: Int) = viewModelScope.launch {
        repository.deleteTodoById(id = todo)
    }
    fun  toggleDone(todo:Todo) = viewModelScope.launch {
        repository.updateTodo(todo)
    }

    suspend fun getTodo(id: Int): Todo = repository.getTodoById(id)





    }
