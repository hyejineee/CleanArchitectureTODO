package com.hyejineee.todo.data.entity

data class TodoEntity(
    val id:Long = 0L,
    val title : String,
    val description :String,
    var hasCompleted : Boolean = false
) {
}