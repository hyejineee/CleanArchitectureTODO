package com.hyejineee.todo.viewmodel.todo

import com.hyejineee.todo.viewmodel.ViewModelTest
import io.kotest.core.test.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * [ListViewModel]을 테스트하기 위한 클래스
 *
 * 1. initData()
 * 2. test fetch data
 * 3. test item update
 * 4. test item delete All
 * */
@ExperimentalCoroutinesApi
class ListViewModelTest:ViewModelTest() {

    /**
     * 필요한 유스케이스
     * 1. insertToDoList
     * 2. getTodoItem
     * */

    private fun initData() {}

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        initData()
    }



    init {

    }
}