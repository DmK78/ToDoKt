package ru.job4j.todokt.tasks


import android.content.Context
import android.content.Intent
import ru.job4j.todokt.BaseActivity
import ru.job4j.todokt.add_task.TaskActivity


class TasksListActivity : BaseActivity() {
    override fun createFragment() = TasksListFragment()

    companion object {
        fun create(
            context: Context?
        ): Intent {
            val intent = Intent(context, TasksListActivity::class.java)
            return intent
        }
    }

}
