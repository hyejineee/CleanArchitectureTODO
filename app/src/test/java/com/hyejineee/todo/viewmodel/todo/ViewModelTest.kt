package com.hyejineee.todo.viewmodel.todo

import android.app.Application
import com.hyejineee.todo.DI.appTestModule
import com.hyejineee.todo.InstantExecutorListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.security.AccessControlContext

@ExperimentalCoroutinesApi
internal class ViewModelTest : DescribeSpec(), KoinTest {

    private var context: Application = mockk<Application>()
    private val dispatcher = TestCoroutineDispatcher()

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)

        startKoin {
            androidContext(context)
            modules(appTestModule)
        }

        Dispatchers.setMain(dispatcher)
    }

    override fun afterSpec(spec: Spec) {
        super.afterSpec(spec)
        stopKoin()
        Dispatchers.resetMain() // 메인 디스패처를 초기화해줘야 메모리 누수가 발생하지 않는다.
    }

    init {
        listener(InstantExecutorListener())
    }
}