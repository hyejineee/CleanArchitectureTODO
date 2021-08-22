package com.hyejineee.todo.viewmodel.todo

import com.hyejineee.todo.Context
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.domain.todo.DeleteTodoListUseCase
import com.hyejineee.todo.domain.todo.GetTodoUseCase
import com.hyejineee.todo.domain.todo.InsertTodoUseCase
import com.hyejineee.todo.presentation.list.ListViewModel
import com.hyejineee.todo.presentation.list.TodoListState
import com.hyejineee.todo.viewmodel.ViewModelTest
import io.kotest.common.ExperimentalKotest
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.koin.test.inject

/**
 * [ListViewModel]을 테스트하기 위한 클래스
 *
 * 1. initData()
 * 2. test fetch data
 * 3. test item update
 * 4. test item delete All
 * */
@ExperimentalKotest
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertTodoUseCase: InsertTodoUseCase by inject()
    private val getTodoUseCase: GetTodoUseCase by inject()
    private val deleteTodoListUseCase: DeleteTodoListUseCase by inject()

    private val mockList = (0 until 9).map {
        TodoEntity(
            id = it.toLong(),
            title = "title${it}",
            description = "description${it}",
            hasCompleted = false
        )
    }

    override fun initData() = runBlockingTest {
        insertTodoUseCase(mockList) // 데이터 초기화
    }

    override fun removeData() = runBlockingTest {
        deleteTodoListUseCase()
    }


    init {

        runBlockingTest {

            describe("ListViewModel ") {
                context("when fetch()is called ").config(tags = setOf(Context)) {
                    val testObservable = viewModel.todoListLiveData.test()
                    it("todoList data is posted to livedata") {

                        viewModel.fetchData()
                        testObservable.assertValueSequence(
                            listOf(
                                TodoListState.UnInitialized,
                                TodoListState.Loading,
                                TodoListState.Success(mockList)
                            )
                        )
                    }
                    viewModel.todoListLiveData.removeTest(testObservable)
                }

                context("when the original todo data changes ").config(tags = setOf(Context)) {
                    it("the todo data is updated") {
                        val todo = TodoEntity(
                            id = 1,
                            title = "title 1",
                            description = " this is changed data",
                            hasCompleted = true
                        )

                        viewModel.updateEntity(todo)

                        getTodoUseCase(todo.id)?.hasCompleted shouldBe true
                    }
                }


                context("when all data are deleted").config(tags = setOf(Context)) {
                    it("todo list is empty") {
                        val testObservable = viewModel.todoListLiveData.test()
                        viewModel.deleteAll()

                        testObservable.assertValueSequence(
                            listOf(
                                TodoListState.Success(listOf()),
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