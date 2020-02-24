package ru.job4j.todokt.add_task

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.job4j.todokt.R
import ru.job4j.todokt.databinding.ActivityTaskBinding
import ru.job4j.todokt.db.Task
import ru.job4j.todokt.tasks.TasksListActivity
import ru.job4j.todokt.tasks.TasksListFragment

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TaskFragment : Fragment() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_task, container, false)
        val taskId = arguments!!.getInt(TasksListFragment.TASK_ID)
        viewModel = ViewModelProviders.of(this)[TaskViewModel::class.java]
        binding.viewmodel = viewModel
        if (taskId != -1) {
            viewModel.getTasksMutableLiveData().observe(viewLifecycleOwner, Observer {
                viewModel.task.set(it)
            })
            viewModel.getTaskById(taskId)
        } else viewModel.task.set(Task(name = "", desc = ""))

        binding.buttonSave.setOnClickListener {
            when (viewModel.task.get()?.id) {
                null -> viewModel.saveTask()
                else -> viewModel.updateTask()
            }
            startActivity(TasksListActivity.create(context))
        }
        binding.buttonCancel.setOnClickListener {
            startActivity(TasksListActivity.create(context))
        }
        binding.buttonDelete.setOnClickListener {
            viewModel.deleteTask()
            startActivity(TasksListActivity.create(context))
        }
        return binding.root
    }

    companion object {
        fun of(
            taskId: Int
        ): TaskFragment =
            TaskFragment().apply {
                arguments = bundleOf(
                    TasksListFragment.TASK_ID to taskId
                )
            }
    }
}