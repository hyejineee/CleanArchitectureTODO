package com.hyejineee.todo.presentation.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyejineee.todo.databinding.ActivityListBinding
import com.hyejineee.todo.presentation.BaseActivity
import com.hyejineee.todo.presentation.TodoAdapter
import com.hyejineee.todo.presentation.detail.DetailActivity
import com.hyejineee.todo.presentation.detail.DetailMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

internal class ListActivity : BaseActivity<ListViewModel>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override val viewModel: ListViewModel by viewModel()

    private lateinit var binding: ActivityListBinding

    private val adapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(binding)

    }


    override fun observeData() {
        viewModel.todoListLiveData.observe(this) {
            Log.d("ListActivity", "state : ${it}")
            when (it) {
                is TodoListState.UnInitialized -> { initViews(binding) }
                is TodoListState.Loading -> handleLoadingState()
                is TodoListState.Success -> handleSuccessState(it)
                is TodoListState.Error -> handleError()
            }
        }
    }

    private fun handleError() {
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: TodoListState.Success) = with(binding) {
        Log.d("ListActivity", "${state.todoList}")
        refreshLayout.isEnabled = state.todoList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.todoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setToDoList(
                state.todoList,
                toDoItemClickListener = {
                    startActivityForResult(
                        DetailActivity.getIntent(this@ListActivity, it.id, DetailMode.DETAIL),
                        DetailActivity.FETCH_REQUEST_CODE
                    )
                }, toDoCheckListener = {
                    viewModel.updateEntity(it)
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.fetchData()
        }
    }

    private fun initViews(binding: ActivityListBinding) = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(this@ListActivity)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        addToDoButton.setOnClickListener {
            startActivityForResult(
                DetailActivity.getIntent(this@ListActivity, DetailMode.WRITE),
                DetailActivity.FETCH_REQUEST_CODE
            )
        }
    }
}