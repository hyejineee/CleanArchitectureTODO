package com.hyejineee.todo.viewmodel.todo

import com.hyejineee.todo.Context
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.data.repository.TestTodoRepository
import com.hyejineee.todo.data.repository.TodoRepository
import com.hyejineee.todo.domain.todo.DeleteTodoListUseCase
import com.hyejineee.todo.domain.todo.InsertTodoUseCase
import com.hyejineee.todo.presentation.detail.DetailMode
import com.hyejineee.todo.presentation.detail.DetailViewModel
import com.hyejineee.todo.presentation.detail.TodoDetailState
import com.hyejineee.todo.presentation.todo.ListViewModel
import com.hyejineee.todo.presentation.todo.TodoListState
import com.hyejineee.todo.viewmodel.ViewModelTest
import io.kotest.common.ExperimentalKotest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.koin.core.parameter.parametersOf
import org.koin.test.get
import org.koin.test.inject

/**
 * [DetailViewModel]를 테스트하기 위한 단위 테스트
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test delete todo
 * 4. test update todo
 * */
@ExperimentalKotest
@ExperimentalCoroutinesApi

internal class DetailViewModelTest : ViewModelTest() {

    private val id = 1L

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var listViewModel: ListViewModel

    private val insertTodoUseCase by inject<InsertTodoUseCase>()
    private val deleteTodoListUseCase by inject<DeleteTodoListUseCase>()

    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "DetailViewModelTest $id",
        hasCompleted = false
    )

    override fun initData() = runBlockingTest {
        detailViewModel = get { parametersOf(DetailMode.DETAIL, id) }
        listViewModel = get()
        insertTodoUseCase(todo)
    }

    override fun removeData() = runBlockingTest {
        deleteTodoListUseCase()
    }

    init {
        runBlockingTest {
            describe("DetailViewModel") {
                context("when fetchData() is called ").config(tags = setOf(Context)) {
                    it("todo item is posted to liveData") {
                        val testObservable = detailViewModel.todoDetailLiveData.test()
                        detailViewModel.fetchData()

                        testObservable.assertValueSequence(
                            listOf(
                                TodoDetailState.UnInitialized,
                                TodoDetailState.Loading,
                                TodoDetailState.Success(todo)
                            )
                        )
                    }
                }

                context("when delete a todo Item").config(tags = setOf(Context)) {
                    it("dff") {
                        val deleteTestObservable = detailViewModel.todoDetailLiveData.test()

                        detailViewModel.delete(id)

                        deleteTestObservable.assertValueSequence(
                            listOf(
                                TodoDetailState.UnInitialized,
                                TodoDetailState.Loading,
                                TodoDetailState.Delete
                            )
                        )

                         val listTestObservable = listViewModel.todoListLiveData.test()
                        listViewModel.fetchData()

                        listTestObservable.assertValueSequence(
                            listOf(
                                TodoListState.UnInitialized,
                                TodoListState.Loading,
                                TodoListState.Success(listOf())
                            )
                        )
                    }
                }
            }
        }

    }
}