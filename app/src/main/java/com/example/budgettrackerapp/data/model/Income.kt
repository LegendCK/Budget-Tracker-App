package com.example.budgettrackerapp.data.model

data class Income(
    val amt: Double = 0.0,
    val desc: String = "",
    val date: String = "",
    val category: String = "Income"
)