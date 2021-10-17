package com.example.todolist.db

import androidx.room.*
import com.example.todolist.data.Task
import com.example.todolist.viewmodel.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when(sortOrder){
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query("SELECT * FROM task_table where (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' order by important Desc, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table where (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' order by important Desc, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}