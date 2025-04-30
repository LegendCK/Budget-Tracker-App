package com.example.budgettrackerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Box
import com.composables.icons.lucide.Lucide
import com.example.budgettrackerapp.data.model.Expense
import com.example.budgettrackerapp.data.model.Income
import com.example.budgettrackerapp.data.repository.CategoryRepository
import com.example.budgettrackerapp.data.repository.TransactionRepository
import kotlinx.coroutines.launch

enum class TransactionTab {
    All, Income, Expense
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(TransactionTab.All) }
    var incomeList by remember { mutableStateOf<List<Income>>(emptyList()) }
    var expenseList by remember { mutableStateOf<List<Expense>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            incomeList = TransactionRepository.getIncome()
            expenseList = TransactionRepository.getExpenses()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions",fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTab.ordinal) {
                TransactionTab.values().forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tab.name) }
                    )
                }
            }

            TransactionList(
                selectedTab = selectedTab,
                incomeList = incomeList,
                expenseList = expenseList
            )
        }
    }
}


@Composable
fun TransactionList(
    selectedTab: TransactionTab,
    incomeList: List<Income>,
    expenseList: List<Expense>
) {
    val allTransactions = remember(incomeList, expenseList) {
        (incomeList.map { it to TransactionTab.Income } + expenseList.map { it to TransactionTab.Expense })
            .sortedBy { pair ->
                when (val txn = pair.first) {
                    is Income -> txn.timestamp
                    is Expense -> txn.timestamp
                    else -> 0L
                }
            }
    }

    val filteredTransactions = when (selectedTab) {
        TransactionTab.Income -> allTransactions.filter { it.second == TransactionTab.Income }
        TransactionTab.Expense -> allTransactions.filter { it.second == TransactionTab.Expense }
        else -> allTransactions
    }

    LazyColumn {
        items(filteredTransactions) { (transaction, type) ->
            TransactionItem(transaction, type)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun TransactionItem(transaction: Any, type: TransactionTab) {
    // Get the category for the transaction
    val category = when (transaction) {
        is Income -> transaction.category
        is Expense -> transaction.category
        else -> "misc"
    }
    val categoryDetails = CategoryRepository.getCategories().find { it.name == category }

    val icon = categoryDetails?.icon ?: Lucide.Box
    val iconTint = categoryDetails?.iconTint ?: Color.White
    val backgroundColor = categoryDetails?.color ?: Color.Gray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                val desc = when (transaction) {
                    is Income -> transaction.desc
                    is Expense -> transaction.desc
                    else -> ""
                }
                val categoryName = when (transaction) {
                    is Income -> transaction.category
                    is Expense -> transaction.category
                    else -> ""
                }

                Text(text = desc, style = MaterialTheme.typography.titleMedium)
                Text(text = categoryName, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            val amt = when (transaction) {
                is Income -> transaction.amt
                is Expense -> transaction.amt
                else -> 0.0
            }

            Text(
                text = "₹ $amt",
                style = MaterialTheme.typography.titleMedium,
                color = if (type == TransactionTab.Income) Color(0xFF10B981) else Color(0xFFEF4444)
            )
        }
    }
}

