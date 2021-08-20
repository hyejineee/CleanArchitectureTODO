package com.hyejineee.todo.data.repository

import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.data.repository.TodoRepository

class TestTodoRepository:TodoRepository {

    private val todoList = mutableListOf<TodoEntity>()

    override suspend fun getTodoList(): List<TodoEntity> {
        return todoList
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        this.todoList.addAll(todoList)
    }
}