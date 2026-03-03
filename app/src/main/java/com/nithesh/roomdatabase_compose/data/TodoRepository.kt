package com.nithesh.roomdatabase_compose.data

import kotlinx.coroutines.flow.Flow

class TodoRepository (private val dao: TodoDao){
    val allTodos: Flow<List<Todo>> = dao.getAllTodos()

    suspend fun addTodo(todo: Todo) = dao.insertTodo(todo)

    suspend fun  deleteTodoById(id: Int) = dao.deleteTodoById(id)
    suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo)

    suspend fun getTodoById(id: Int): Todo = dao.getTodoById(id)




}