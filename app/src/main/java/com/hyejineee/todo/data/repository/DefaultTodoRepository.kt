package com.hyejineee.todo.data.repository

import com.hyejineee.todo.data.entity.TodoEntity

class DefaultTodoRepository:TodoRepository {
    override suspend fun getTodoList(): List<TodoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodo(todoEntity: TodoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoItem(todoEntity: TodoEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTodo(id: Long): TodoEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}