package com.hyejineee.todo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.hyejineee.todo.Context
import com.hyejineee.todo.Describe
import com.hyejineee.todo.InstantExecutorListener
import com.hyejineee.todo.di.appTestModule
import com.hyejineee.todo.livedata.LiveDataTestObserver
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

@ExperimentalCoroutinesApi
internal abstract class ViewModelTest : DescribeSpec(), KoinTest {

    private var context: Application = mockk<Application>()
    private val dispatcher = TestCoroutineDispatcher()


    protected abstract fun initData()
    protected abstract fun removeData()

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

    override fun beforeContainer(testCase: TestCase) {

        if (!testCase.config.tags.contains(Context)) {
            return
        }
        super.beforeContainer(testCase)
        initData()

    }

    override fun afterContainer(testCase: TestCase, result: TestResult) {
        if (!testCase.config.tags.contains(Context)) {
            return
        }
        super.afterContainer(testCase, result)

        removeData()
    }

    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        this.observeForever(testObserver)
        return testObserver
    }

    protected fun <T> LiveData<T>.removeTest(observer: LiveDataTestObserver<T>) {
        this.removeObserver(observer)
    }

    init {
        listener(InstantExecutorListener())
    }
}