package com.hyejineee.todo.viewmodel.todo

import com.hyejineee.todo.Context
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.presentation.detail.DetailMode
import com.hyejineee.todo.presentation.detail.DetailViewModel
import com.hyejineee.todo.presentation.detail.TodoDetailState
import com.hyejineee.todo.presentation.todo.ListViewModel
import com.hyejineee.todo.presentation.todo.TodoListState
import com.hyejineee.todo.viewmodel.ViewModelTest
import io.kotest.common.ExperimentalKotest
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.koin.core.parameter.parametersOf
import org.koin.test.get

/**
 * 1. test viewmodel fetch
 * 2. test insert todo
 *
 * **/
@ExperimentalKotest
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteModeTest : ViewModelTest() {

    private val id = 0L

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var listViewModel: ListViewModel

    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "DetailViewModelTest $id",
        hasCompleted = false
    )

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        detailViewModel = get { parametersOf(DetailMode.WRITE, id) }
        listViewModel = get()
    }

    override fun initData() {}

    override fun removeData() {}

    init {
        runBlockingTest {
            describe("DetailViewModel test for write mode") {

                it("is fetched WRITE mode") {
                    val testObservable = detailViewModel.todoDetailLiveData.test()

                    detailViewModel.fetchData()

                    testObservable.assertValueSequence(
                        listOf(
                            TodoDetailState.UnInitialized,
                            TodoDetailState.Write
                        )
                    )

                }

                it("insert todo item to repo"){
                    val detailTestObservable = detailViewModel.todoDetailLiveData.test()
                    val listTestObservable = listViewModel.todoListLiveData.test()

                    detailViewModel.writeTodo(
                        title = todo.title,
                        description = todo.description
                    )

                    detailTestObservable.assertValueSequence(
                        listOf(
                            TodoDetailState.UnInitialized,
                            TodoDetailState.Loading,
                            TodoDetailState.Success(todo)
                        )
                    )

                    detailViewModel.detailMode shouldBe DetailMode.DETAIL
                    detailViewModel.id shouldBe id
                    
                    listViewModel.fetchData()
                    listTestObservable.assertValueSequence(
                        listOf(
                            TodoListState.UnInitialized,
                            TodoListState.Loading,
                            TodoListState.Success(listOf(todo))
                        )
                    )

                }
            }
        }
    }
}