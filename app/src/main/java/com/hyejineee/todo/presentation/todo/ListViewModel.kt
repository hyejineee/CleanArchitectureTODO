package com.hyejineee.todo.presentation.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.domain.todo.DeleteTodoListUseCase
import com.hyejineee.todo.domain.todo.GetTodoListUseCase
import com.hyejineee.todo.domain.todo.UpdateTodoUseCase
import com.hyejineee.todo.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 필요한 유스케이스
 * 1. [GetToDoUseCase]
 * 2. [UpdateToDoUseCase]
 * 3. [DeleteAllToDoUseCase]
 * */
internal class ListViewModel(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoListUseCase:DeleteTodoListUseCase,
) : BaseViewModel() {

    private var _todoListLiveData = MutableLiveData<TodoListState>(TodoListState.UnInitialized)
    val todoListLiveData: LiveData<TodoListState> = _todoListLiveData

    override fun fetchData() = viewModelScope.launch {
        _todoListLiveData.value = TodoListState.Loading
        _todoListLiveData.value = TodoListState.Success(getTodoListUseCase())
    }

    fun updateEntity(todoEntity: TodoEntity) = viewModelScope.launch {
        updateTodoUseCase(todoEntity)
    }

    fun deleteAll():Job = viewModelScope.launch {
        _todoListLiveData.value = TodoListState.Loading
        deleteTodoListUseCase()
        _todoListLiveData.value = TodoListState.Success(listOf())
    }
}
