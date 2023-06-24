package com.example.myapplication.todoapp

data class Task (var name: String, val category: TaskCategory, var isSelected: Boolean = false)