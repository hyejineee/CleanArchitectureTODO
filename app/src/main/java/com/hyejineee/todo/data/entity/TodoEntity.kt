package com.hyejineee.todo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id:Long =0L,
    val title : String,
    val description :String,
    var hasCompleted : Boolean = false
) {
}