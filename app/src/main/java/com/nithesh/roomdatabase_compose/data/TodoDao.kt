package com.nithesh.roomdatabase_compose.data

import androidx.room.Dao

import androidx.room.Insert

import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<Todo>>

@Insert
suspend fun insertTodo(todo: Todo)

@Query("DELETE FROM todos WHERE id = :id")
suspend fun deleteTodoById(id: Int)

@Update
suspend fun updateTodo(todo: Todo)

@Query("SELECT * FROM todos WHERE id = :id")
suspend fun getTodoById(id: Int): Todo


}
