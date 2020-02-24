package ru.job4j.todokt.tasks


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tasks_list.view.*
import ru.job4j.todokt.R
import ru.job4j.todokt.add_task.TaskActivity

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
        setHasOptionsMenu(true);
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAllTasks -> if (tasksAdapter.itemCount > 0) deleteAllTasks()

        }



        return super.onOptionsItemSelected(item)
    }

    fun setupAdapter(view: View) {
        val recyclerView = view.findViewById(R.id.recyclerViewTasks) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        tasksAdapter = TasksAdapter {
            startActivity(TaskActivity.create(view.context, it.id!!))
        }
        recyclerView.adapter = tasksAdapter
    }

    fun deleteAllTasks() {
        AlertDialog.Builder(context!!)
            .setTitle("Confirmation")
            .setMessage("Do you really want to clear all records?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, whichButton ->
                viewModel.deleteAllTasks()

            }
            .setNegativeButton(android.R.string.no, null).show()


    }

    companion object {
        val TASK_ID: String = "task_id"

    }
}