package com.hyejineee.todo.data.local.db.dao

import androidx.room.*
import com.hyejineee.todo.data.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("select * from TodoEntity")
    suspend fun getAll():List<TodoEntity>

    @Query("select * from TodoEntity where id=:id")
    suspend fun getById(id:Long):TodoEntity?

    @Insert
    suspend fun insert(todoEntity: TodoEntity) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntityList: List<TodoEntity>)

    @Query("delete from TodoEntity where id=:id")
    suspend fun delete(id:Long):Int

    @Query("delete from TodoEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(todoEntity: TodoEntity):Int
}
