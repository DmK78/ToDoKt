package ru.job4j.todokt.tasks


import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import ru.job4j.todokt.BaseActivity
import ru.job4j.todokt.R

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

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
