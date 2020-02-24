package ru.job4j.todokt.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_item.view.*
import ru.job4j.todokt.R
import ru.job4j.todokt.db.Task


/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TasksAdapter(
    private val itemClick: (Task) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TracksHolder>() {
    private val tasks: ArrayList<Task> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TracksHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_item,
            parent, false
        )
        return TracksHolder(
            itemView
        )
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TracksHolder, position: Int) {
        val currentTask = tasks[position]
        holder.setup(currentTask, itemClick)
    }

    fun setData(tracksList: List<Task>) {
        tasks.clear()
        tasks.addAll(tracksList)
        notifyDataSetChanged()
    }

    class TracksHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setup(task: Task, itemClick: (Task) -> Unit) {
            with(containerView) {
                editTextName.setText(task.name)
                editTextDesc.setText(task.desc)
                val taskDate = task.created
                textViewCreated.setText(
                    taskDate!!.substring(
                        taskDate.indexOf(',') + 1,
                        taskDate.lastIndexOf(',')
                    )
                )
                textViewClosed.setText(task.closed)
                setOnClickListener { itemClick.invoke(task) }


            }
        }
    }
}