package com.hyejineee.todo.di

import com.hyejineee.todo.data.repository.TestTodoRepository
import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.todo.GetTodoUseCase
import com.hyejineee.todo.domain.todo.InsertTodoUseCase
import org.koin.dsl.module

internal val appTestModule = module {

    //usecase
    factory { GetTodoUseCase(get()) }
    factory { InsertTodoUseCase(get()) }

    //repository
    single<TodoRepository> { TestTodoRepository() }
}