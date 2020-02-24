package ru.job4j.todokt.add_task

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.job4j.todokt.BaseActivity
import ru.job4j.todokt.tasks.TasksListFragment

class TaskActivity : BaseActivity() {
    override fun createFragment(): Fragment {

        return TaskFragment.of(intent.getIntExtra(TasksListFragment.TASK_ID,0))
    }

    companion object {
        fun create(
            context: Context?, taskId: Int
        ): Intent {
            val intent = Intent(context, TaskActivity::class.java)
            intent.putExtra(TasksListFragment.TASK_ID, taskId)
            return intent
        }
    }


}
