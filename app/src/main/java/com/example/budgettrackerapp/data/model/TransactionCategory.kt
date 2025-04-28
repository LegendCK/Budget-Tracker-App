package com.example.budgettrackerapp.data.model

import androidx.compose.ui.graphics.Color

data class TransactionCategory(
    val id: String,
    val name: String,
    val color: Color,
    val iconName: String
)
