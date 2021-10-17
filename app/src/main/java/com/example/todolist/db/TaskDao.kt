package com.example.todolist.db

import androidx.room.*
import com.example.todolist.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("Select * from task_table")
    fun getTasks(): Flow<List<Task>> // asynchronous stream of data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}