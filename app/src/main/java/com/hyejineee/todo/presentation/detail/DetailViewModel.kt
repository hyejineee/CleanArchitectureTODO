package com.hyejineee.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyejineee.todo.data.entity.TodoEntity
import com.hyejineee.todo.domain.todo.DeleteTodoUseCase
import com.hyejineee.todo.domain.todo.GetTodoUseCase
import com.hyejineee.todo.domain.todo.InsertTodoUseCase
import com.hyejineee.todo.domain.todo.UpdateTodoUseCase
import com.hyejineee.todo.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getTodoItemUseCase: GetTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase
) : BaseViewModel() {

    private var _todoDetailLiveData =
        MutableLiveData<TodoDetailState>(TodoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<TodoDetailState> = _todoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {

        when (detailMode) {
            DetailMode.DETAIL -> {
                _todoDetailLiveData.postValue( TodoDetailState.Loading)
                try {
                    getTodoItemUseCase(id)?.let {
                        _todoDetailLiveData.postValue(TodoDetailState.Success(it))
                    } ?: kotlin.run {
                        _todoDetailLiveData.postValue(TodoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)
                }
            }
            DetailMode.WRITE -> {
                _todoDetailLiveData.postValue(TodoDetailState.Write)
            }
        }
    }

    fun deleteToDo() = viewModelScope.launch {
        _todoDetailLiveData.postValue(TodoDetailState.Loading)
        try {
            deleteTodoUseCase(id)
            _todoDetailLiveData.postValue(TodoDetailState.Delete)
        } catch (e: Exception) {
            e.printStackTrace()
            _todoDetailLiveData.postValue(TodoDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _todoDetailLiveData.postValue(TodoDetailState.Modify)
    }

    fun writeTodo(title: String, description: String) = viewModelScope.launch {
        _todoDetailLiveData.postValue(TodoDetailState.Loading)
        when (detailMode) {
            DetailMode.DETAIL -> {
                try {
                    getTodoItemUseCase(id)?.let {
                        val updateTodoEntity = it.copy(
                            title = title,
                            description = description
                        )

                        updateTodoUseCase(updateTodoEntity)
                        _todoDetailLiveData.postValue(TodoDetailState.Success(updateTodoEntity))
                    } ?: kotlin.run {
                        _todoDetailLiveData.postValue(TodoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)

                }


            }
            DetailMode.WRITE -> {
                try {
                    val todoEntity = TodoEntity(
                        title = title,
                        description = description
                    )

                    id = insertTodoUseCase(todoEntity)
                    _todoDetailLiveData.postValue(TodoDetailState.Success(todoEntity))

                }catch (e:Exception){
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)
                }

                detailMode = DetailMode.DETAIL
            }
        }
    }


}