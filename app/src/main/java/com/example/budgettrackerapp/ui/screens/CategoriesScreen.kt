package com.example.budgettrackerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettrackerapp.data.model.Expense
import com.example.budgettrackerapp.data.model.TransactionCategory
import com.example.budgettrackerapp.data.repository.CategoryRepository
import com.example.budgettrackerapp.data.repository.TransactionRepository

@Preview(
    showBackground = true,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    var categories by remember { mutableStateOf<List<TransactionCategory>>(emptyList()) }
    var spendingAmounts by remember { mutableStateOf<Map<String, Float>>(emptyMap()) }

    LaunchedEffect(Unit) {
        categories = CategoryRepository.getCategories()

        val expenses = TransactionRepository.getExpenses()

        spendingAmounts = calculateSpendingAmounts(expenses, categories)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Categories", fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            categories.forEach { category ->
                val spendingAmount = spendingAmounts[category.name] ?: 0f
                CategoryItem(
                    category = category,
                    spendingAmount = spendingAmount
                ) {
                    // Handle item click to show details (optional)
                }
            }
        }
    }
}

fun calculateSpendingAmounts(
    expenses: List<Expense>,
    categories: List<TransactionCategory>
): Map<String, Float> {
    val spendingAmounts = mutableMapOf<String, Float>()
    expenses.groupBy { it.category }.forEach { (categoryId, expensesInCategory) ->

        val totalSpending = expensesInCategory.sumOf { it.amt }

        spendingAmounts[categoryId] = totalSpending.toFloat()
    }

    return spendingAmounts
}

@Composable
fun CategoryItem(
    category: TransactionCategory,
    spendingAmount: Float,
    onClick: () -> Unit
) {
    val icon = category.icon
    val iconTint = category.iconTint

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(category.color, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "$${spendingAmount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

