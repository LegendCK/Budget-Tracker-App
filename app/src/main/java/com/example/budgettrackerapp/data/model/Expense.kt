package com.example.budgettrackerapp.data.model

data class Expense(
    val amt: Double = 0.0,
    val desc: String = "",
    val date: String = "",
    val category: String = "",
    val timestamp: Long = System.currentTimeMillis()
)