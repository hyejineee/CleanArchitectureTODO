package com.hyejineee.todo.domain.todo

import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.UseCase

class DeleteTodoUseCase(
    private val repository: TodoRepository
):UseCase {

    suspend operator fun invoke(id:Long) = repository.delete(id)

}