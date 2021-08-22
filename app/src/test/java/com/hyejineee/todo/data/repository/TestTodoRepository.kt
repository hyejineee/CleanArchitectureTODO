package com.hyejineee.todo.data.repository

import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.data.repository.TodoRepository

class TestTodoRepository:TodoRepository {

    val todoList = mutableListOf<TodoEntity>()

    override suspend fun getTodoList(): List<TodoEntity> {
        return todoList
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        this.todoList.addAll(todoList)
    }

    override suspend fun insertTodo(todoEntity: TodoEntity) {
       this.todoList.add(todoEntity)
    }

    override suspend fun updateTodoItem(todoEntity: TodoEntity): Boolean {
        todoList.find { it.id == todoEntity.id }?.run {
            todoList[todoList.indexOf(this)] = todoEntity
            return true
        } ?: return false
    }

    override suspend fun getTodo(id: Long): TodoEntity? {
        return this.todoList.find { it.id == id}
    }

    override suspend fun deleteAll() {
        this.todoList.clear()
    }

    override suspend fun delete(id: Long): Boolean {
        todoList.find { it.id == id }?.run {
            todoList.remove(this)
            return true
        } ?: return false
    }


}