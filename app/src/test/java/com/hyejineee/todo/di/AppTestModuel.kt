package com.hyejineee.todo.di

import com.hyejineee.todo.data.repository.TestTodoRepository
import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.todo.*
import com.hyejineee.todo.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //usecase
    factory { GetTodoListUseCase(get()) }
    factory { InsertTodoUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { GetTodoUseCase(get()) }
    factory { DeleteTodoListUseCase(get()) }

    //repository
    single<TodoRepository> { TestTodoRepository() }

    //viewModel
    viewModel { ListViewModel(get(), get(), get()) }
}