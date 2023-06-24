package com.example.myapplication.todoapp

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.todoapp.TaskCategory.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {

    private val categories = listOf(
        Business,
        Personal,
        Other
    )
    private val tasks = mutableListOf(
        Task("Negocio", Business),
        Task("Personal", Personal),
        Task("Otro", Other)
    )
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var fabAddTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        initComponents()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)

        btnAddTask.setOnClickListener {
            val currentTask = etTask.text.toString()
            if (currentTask.isNotEmpty()) {
                val selectedId = rgCategories.checkedRadioButtonId
                val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)
                val currentCategory: TaskCategory = when(selectedRadioButton.text) {
                    getString(R.string.todo_business) -> Business
                    getString(R.string.todo_personal) -> Personal
                    else -> Other
                }
                tasks.add(Task(etTask.text.toString(), currentCategory))
                updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    private fun onEditTask(position: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_edit)

        val etEditText: EditText = dialog.findViewById(R.id.etEditTask)
        val btnSaveTask: Button = dialog.findViewById(R.id.btnSaveTask)

        btnSaveTask.setOnClickListener {
            val editedTask = etEditText.text.toString()
            if (editedTask.isNotEmpty()) {
                tasks[position].name = editedTask
                updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    private fun onDeleteTask(position: Int) {
        tasks.removeAt(position)
        updateTasks()
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) { updateCategories(it) }
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks, {onItemSelected(it)}, {onEditTask(it)}, {onDeleteTask(it)})
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter
    }

    private fun onItemSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }

    private fun updateCategories(position: Int) {
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }

    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun updateTasks() {
        val selectedCategories: List<TaskCategory> = categories.filter { it.isSelected }
        val newTasks = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = newTasks
        tasksAdapter.notifyDataSetChanged()
    }
}