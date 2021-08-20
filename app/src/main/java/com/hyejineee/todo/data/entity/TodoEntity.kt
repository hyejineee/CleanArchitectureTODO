package com.hyejineee.todo.data.entity

data class TodoEntity(
    val id:Long,
    val title : String,
    val description :String,
    var hasCompleted : Boolean
) {
}