package com.hyejineee.todo.data.repository

import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.data.local.db.dao.TodoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultTodoRepository(
    private val todoDao: TodoDao,
    private val ioDispatcher: CoroutineDispatcher
) : TodoRepository {
    override suspend fun getTodoList(): List<TodoEntity> = withContext(ioDispatcher) {
        todoDao.getAll()
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) = withContext(ioDispatcher) {
        todoDao.insert(todoList)
    }

    override suspend fun insertTodo(todoEntity: TodoEntity): Long = withContext(ioDispatcher) {
        todoDao.insert(todoEntity)
    }

    override suspend fun updateTodoItem(todoEntity: TodoEntity): Boolean =
        withContext(ioDispatcher) {
            todoDao.update(todoEntity) > -1
        }

    override suspend fun getTodo(id: Long): TodoEntity? = withContext(ioDispatcher) {
        todoDao.getById(id)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        todoDao.deleteAll()
    }

    override suspend fun delete(id: Long): Boolean = withContext(ioDispatcher) {
        todoDao.delete(id) > -1
    }
}