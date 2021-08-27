package com.hyejineee.todo.DI

import android.content.Context
import androidx.room.Room
import com.hyejineee.todo.data.local.db.dao.TodoDataBase
import com.hyejineee.todo.data.repository.DefaultTodoRepository
import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.todo.*
import com.hyejineee.todo.presentation.detail.DetailMode
import com.hyejineee.todo.presentation.detail.DetailViewModel
import com.hyejineee.todo.presentation.todo.ListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module{

    //usecase
    factory { GetTodoListUseCase(get()) }
    factory { GetTodoUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }
    factory { InsertTodoUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { DeleteTodoListUseCase(get()) }
    factory { DeleteTodoUseCase(get()) }

    //room
    single { provideDb(androidApplication()) }
    single { provideTodoDao(get()) }

    //repository
    single<TodoRepository> { DefaultTodoRepository(get(), Dispatchers.IO) }

    //viewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id:Long) ->
        DetailViewModel(
            detailMode = detailMode,
            id = id,
            getTodoItemUseCase = get(),
            deleteTodoUseCase = get(),
            updateTodoUseCase = get(),
            get()
        )
    }
}

internal fun provideDb(context:Context):TodoDataBase =
    Room.inMemoryDatabaseBuilder(context, TodoDataBase::class.java).build()

internal fun provideTodoDao(dataBase: TodoDataBase) = dataBase.todoDao()