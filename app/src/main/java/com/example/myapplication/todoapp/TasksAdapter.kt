package com.example.myapplication.todoapp

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class TasksAdapter(var tasks: List<Task>, private val onTaskSelected: (Int) -> Unit, private val onEditTask: (Int) -> Unit, private val onDeleteTask: (Int) -> Unit) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTask: TextView = view.findViewById(R.id.tvTask)
        private val cbTask: CheckBox = view.findViewById(R.id.cbTask)

        fun render(task: Task) {
            if (task.isSelected) {
                tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            cbTask.isChecked = task.isSelected
            tvTask.text = task.name

            val color = when(task.category) {
                TaskCategory.Business -> R.color.todo_business_category
                TaskCategory.Other -> R.color.todo_other_category
                TaskCategory.Personal -> R.color.todo_personal_category
            }
            cbTask.buttonTintList = ColorStateList.valueOf(
                ContextCompat.getColor(cbTask.context, color)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val imgEditButton = holder.itemView.findViewById<ImageButton>(R.id.btnEditTask)
        val imgDeleteButton = holder.itemView.findViewById<ImageButton>(R.id.btnDeleteTask)
        imgEditButton.setOnClickListener { onEditTask(position) }
        imgDeleteButton.setOnClickListener { onDeleteTask(position) }

        holder.itemView.setOnClickListener { onTaskSelected(position) }
        holder.render(tasks[position])
    }

}