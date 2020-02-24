package ru.job4j.todokt.tasks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tasks_list.view.*
import ru.job4j.todokt.R
import ru.job4j.todokt.add_task.TaskActivity
import ru.job4j.todokt.db.Task

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TasksListFragment : Fragment() {
    private lateinit var viewModel: TasksListViewModel
    private lateinit var tasksAdapter: TasksAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_tasks_list, container, false)
        viewModel = ViewModelProviders.of(this)[TasksListViewModel::class.java]
        viewModel.getTasksMutableLiveData().observe(viewLifecycleOwner, Observer {
            tasksAdapter.setData(it)
        })
        viewModel.getAllTasks()
        setupAdapter(view)
        val buttonAddTask = view.floatingActionButton
        buttonAddTask.setOnClickListener {
            startActivity(TaskActivity.create(view.context, -1))
        }



        return view
    }


    fun setupAdapter(view: View) {
        val recyclerView = view.findViewById(R.id.recyclerViewTasks) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        tasksAdapter = TasksAdapter {
            startActivity(TaskActivity.create(view.context, it.id!!))
        }
        recyclerView.adapter = tasksAdapter
    }

    companion object {
        val TASK_ID: String = "task_id"

    }


}