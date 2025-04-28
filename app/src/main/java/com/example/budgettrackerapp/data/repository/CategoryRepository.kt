package com.example.budgettrackerapp.data.repository

import androidx.compose.ui.graphics.Color
import com.example.budgettrackerapp.data.model.TransactionCategory

object CategoryRepository {

    fun getCategories(): List<TransactionCategory> {
        return listOf(
            TransactionCategory(
                id = "food",
                name = "Food",
                color = Color(0xFFEF4444),
                iconName = "utensils"
            ),
            TransactionCategory(
                id = "transport",
                name = "Transport",
                color = Color(0xFF10B981),
                iconName = "car"
            ),
            TransactionCategory(
                id = "bills",
                name = "Bills",
                color = Color(0xFF3B82F6),
                iconName = "zap"
            ),
            TransactionCategory(
                id = "entertainment",
                name = "Entertainment",
                color = Color(0xFF8B5CF6),
                iconName = "film"
            ),
            TransactionCategory(
                id = "groceries",
                name = "Groceries",
                color = Color(0xFFF59E0B),
                iconName = "shopping-cart"
            ),
            TransactionCategory(
                id = "health",
                name = "Health",
                color = Color(0xFFEC4899),
                iconName = "heart"
            ),
            TransactionCategory(
                id = "shopping",
                name = "Shopping",
                color = Color(0xFFF97316),
                iconName = "shopping-bag"
            ),
            TransactionCategory(
                id = "misc",
                name = "Misc",
                color = Color(0xFF6B7280),
                iconName = "box"
            ),
        )
    }
}
