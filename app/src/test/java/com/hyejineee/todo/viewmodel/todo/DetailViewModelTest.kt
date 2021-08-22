package com.hyejineee.todo.viewmodel.todo

import com.hyejineee.todo.InstantExecutorListener
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.domain.todo.InsertTodoUseCase
import com.hyejineee.todo.presentation.detail.DetailMode
import com.hyejineee.todo.presentation.detail.DetailViewModel
import com.hyejineee.todo.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

@ExperimentalCoroutinesApi
internal class DetailViewModelTest:ViewModelTest() {

    private val id = 1L

    private val detailViewModel by inject<DetailViewModel> { parametersOf(DetailMode.DETAIL) }

    private val insertTodoUseCase by inject<InsertTodoUseCase> ()

    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false
    )

    override fun initData() = runBlockingTest {
        insertTodoUseCase(todo)
    }

    override fun removeData() {
        // TODO : 하나의 데이터 삭제하는 유스케이스 구현.
    }

    init {
        listener(InstantExecutorListener())
    }
}