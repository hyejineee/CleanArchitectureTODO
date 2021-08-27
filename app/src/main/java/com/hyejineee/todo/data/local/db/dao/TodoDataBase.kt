package com.hyejineee.todo.data.local.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyejineee.todo.data.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDataBase : RoomDatabase() {
    companion object {
        const val DB_NAME = "TodoDataBase.db"
    }

    abstract fun todoDao(): TodoDao
}