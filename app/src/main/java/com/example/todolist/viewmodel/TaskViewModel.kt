package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todolist.db.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

    //this will trigger the flatMapLatest operator
    private val tasksFLow =
        combine( // combine will combine into one flow send all the three latest value and execute (it)
            searchQuery,
            sortOrder,
            hideCompleted
        ) { query, sortOrder, hideCompleted ->
            Triple(query, sortOrder, hideCompleted)
        }.flatMapLatest { (query, sortOrder, hideCompleted) ->
            taskDao.getTasks(query, sortOrder, hideCompleted)
        }

    val tasks = tasksFLow.asLiveData()
}

enum class SortOrder { BY_NAME, BY_DATE }