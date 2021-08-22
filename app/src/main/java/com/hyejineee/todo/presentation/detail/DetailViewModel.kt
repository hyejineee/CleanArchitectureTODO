package com.hyejineee.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyejineee.todo.domain.todo.DeleteTodoUseCase
import com.hyejineee.todo.domain.todo.GetTodoUseCase
import com.hyejineee.todo.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id:Long = -1,
    private val getTodoItemUseCase: GetTodoUseCase,
    private val deleteTodoUseCase : DeleteTodoUseCase
): BaseViewModel() {

    private var _todoDetailLiveData = MutableLiveData<TodoDetailState>(TodoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<TodoDetailState> = _todoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        when(detailMode){
            DetailMode.DETAIL ->{
                _todoDetailLiveData.value = TodoDetailState.Loading

                try {
                    getTodoItemUseCase(id)?.let {
                        _todoDetailLiveData.value = TodoDetailState.Success(it)
                    }?: kotlin.run {
                        _todoDetailLiveData.value = TodoDetailState.Error
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    _todoDetailLiveData.value = TodoDetailState.Error
                }
            }
            DetailMode.WRITE ->{
                // TODO : 나중에 작성모드로 상세화면 진입 로직 처리
            }
        }
    }

    fun delete(id: Long)  = viewModelScope.launch {
        _todoDetailLiveData.value = TodoDetailState.Loading

        try {
            if(deleteTodoUseCase(id)){
                _todoDetailLiveData.value = TodoDetailState.Delete
            }else{
                _todoDetailLiveData.value = TodoDetailState.Error
            }

        }catch (e:Exception){
            e.printStackTrace()
            _todoDetailLiveData.value = TodoDetailState.Error
        }



    }


}