package com.example.budgettrackerapp.data.repository

import androidx.compose.ui.graphics.Color
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.*
import com.example.budgettrackerapp.data.model.TransactionCategory

object CategoryRepository {

    fun getCategories(): List<TransactionCategory> {
        return listOf(
            TransactionCategory(
                id = "food",
                name = "Food",
                color = Color(0xFFEF4444),
                icon = Lucide.Utensils,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "transport",
                name = "Transport",
                color = Color(0xFF10B981),
                icon = Lucide.Car,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "bills",
                name = "Bills",
                color = Color(0xFF3B82F6),
                icon = Lucide.Zap,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "entertainment",
                name = "Entertainment",
                color = Color(0xFF8B5CF6),
                icon = Lucide.Film,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "groceries",
                name = "Groceries",
                color = Color(0xFFF59E0B),
                icon = Lucide.ShoppingCart,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "health",
                name = "Health",
                color = Color(0xFFEC4899),
                icon = Lucide.Heart,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "shopping",
                name = "Shopping",
                color = Color(0xFFF97316),
                icon = Lucide.ShoppingBag,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "misc",
                name = "Misc",
                color = Color(0xFF6B7280),
                icon = Lucide.Box,
                iconTint = Color.White
            ),
            TransactionCategory(
                id = "income",
                name = "Income",
                color = Color(0xFF10B981),
                icon = Lucide.DollarSign,
                iconTint = Color.White
            ),
        )
    }
}
