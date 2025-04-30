package com.example.budgettrackerapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TransactionCategory(
    val id: String,
    val name: String,
    val color: Color,
    val icon : ImageVector,
    val iconTint : Color
)
