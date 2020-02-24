package ru.job4j.todokt.add_task

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.job4j.todokt.db.Task
import ru.job4j.todokt.db.TasksRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private var tasksRepository: TasksRepository = TasksRepository.getInstance(application)
    private val taskMutableLiveData = MutableLiveData<Task>()
    var task: ObservableField<Task> = ObservableField()

    fun getTaskById(taskId: Int) = tasksRepository.getTaskById(taskId, taskMutableLiveData)

    fun getTasksMutableLiveData() = taskMutableLiveData

    fun saveTask() = tasksRepository.addTask(task.get())

    fun deleteTask() = tasksRepository.deleteTask(task.get())

    fun updateTask() = tasksRepository.updateTask(task.get())

/*    fun getTask() = task.get()

    fun setTask(task: Task) = this.task.set(task)*/


}


