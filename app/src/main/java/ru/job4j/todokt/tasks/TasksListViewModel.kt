package ru.job4j.todokt.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.job4j.todokt.db.Task
import ru.job4j.todokt.db.TasksRepository

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TasksListViewModel(application: Application) : AndroidViewModel(application) {

    var tasksRepository: TasksRepository? = TasksRepository.getInstance(application)
    private val tasksListMutableLiveData = MutableLiveData<List<Task?>?>()

    fun getAllTasks() {
        tasksRepository!!.getAllTasks(tasksListMutableLiveData)

    }

    fun addTask(task: Task) {

        tasksRepository!!.addTask(task)
    }

    fun deleteAllTasks() = tasksRepository!!.deleteAllTasks()


fun getTasksMutableLiveData() = tasksListMutableLiveData
}