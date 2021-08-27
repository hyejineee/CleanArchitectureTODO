package com.hyejineee.todo.data.repository

import com.hyejineee.todo.data.entity.TodoEntity

/**
 * 1. insertTodoList
 * 2. getTodoList
 * */
interface TodoRepository {

    suspend fun getTodoList():List<TodoEntity>

    suspend fun insertTodoList(todoList: List<TodoEntity>)

    suspend fun insertTodo(todoEntity: TodoEntity):Long

    suspend fun updateTodoItem(todoEntity: TodoEntity):Boolean

    suspend fun getTodo(id:Long):TodoEntity?

    suspend fun deleteAll()


    suspend fun delete(id: Long): Boolean
}