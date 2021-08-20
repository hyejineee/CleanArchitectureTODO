package com.hyejineee.todo.domain.todo

import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.UseCase

class InsertTodoUseCase(
    private val repository: TodoRepository
):UseCase {
}