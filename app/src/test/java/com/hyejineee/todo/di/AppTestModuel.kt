package com.hyejineee.todo.di

import com.hyejineee.todo.data.repository.TestTodoRepository
import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.todo.*
import com.hyejineee.todo.presentation.detail.DetailMode
import com.hyejineee.todo.presentation.detail.DetailViewModel
import com.hyejineee.todo.presentation.todo.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //usecase
    factory { GetTodoListUseCase(get()) }
    factory { GetTodoUseCase(get()) }

    factory { InsertTodoListUseCase(get()) }
    factory { InsertTodoUseCase(get()) }

    factory { UpdateTodoUseCase(get()) }

    factory { DeleteTodoListUseCase(get()) }
    factory { DeleteTodoUseCase(get()) }


    //repository
    single<TodoRepository> { TestTodoRepository() }

    //viewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode:DetailMode, id:Long) ->
        DetailViewModel(
            detailMode = detailMode,
            id = id,
            getTodoItemUseCase = get(),
            deleteTodoUseCase = get()
        )
    }
}