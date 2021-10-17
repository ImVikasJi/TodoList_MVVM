package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todolist.db.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

}