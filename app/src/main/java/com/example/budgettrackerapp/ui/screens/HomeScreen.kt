package com.example.budgettrackerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.composables.icons.lucide.ArrowDownRight
import com.composables.icons.lucide.ArrowUpRight
import com.composables.icons.lucide.Lucide
import com.example.budgettrackerapp.data.model.Expense
import com.example.budgettrackerapp.data.repository.CategoryRepository
import com.example.budgettrackerapp.data.repository.TransactionRepository

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var income by remember { mutableStateOf(0.0) }
    var expense by remember { mutableStateOf(0.0) }
    val balance = income - expense

    LaunchedEffect(Unit) {
        val incomeList = TransactionRepository.getIncome()
        val expenseList = TransactionRepository.getExpenses()

        income = incomeList.sumOf { it.amt }
        expense = expenseList.sumOf { it.amt }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard",fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(start = 16.dp, end = 16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Current Balance", fontWeight = FontWeight.Bold)
                    Text("₹ ${String.format("%.2f", balance)}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.displayMedium)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF34C759).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Lucide.ArrowUpRight,
                                    contentDescription = "Income Arrow",
                                    tint = Color(0xFF34C759)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Income", color = Color(0xFF34C759))
                                Text("₹ ${String.format("%.2f", income)}", fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF9500).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Lucide.ArrowDownRight,
                                    contentDescription = "Expense Arrow",
                                    tint = Color(0xFFFF9500)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Expense", color = Color(0xFFFF9500))
                                Text("₹ ${String.format("%.2f", expense)}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(16.dp))

           ExpenseSummaryCard()

        }
    }
}

@Composable
fun ExpenseSummaryCard() {
    var expenses by remember { mutableStateOf<List<Expense>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        expenses = TransactionRepository.getExpenses()
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (expenses.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No expenses found!", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        val slices = remember(expenses) { preparePieChartSlices(expenses) }

        val pieChartData = PieChartData(
            slices = slices,
            plotType = PlotType.Pie
        )

        val pieChartConfig = PieChartConfig(
            startAngle = 0f,
            showSliceLabels = true,
            sliceLabelTextSize = 12.sp,
            isAnimationEnable = true,
            labelVisible = true,
            labelType = PieChartConfig.LabelType.PERCENTAGE,
            backgroundColor = Color.Transparent,
            isSumVisible = true,
            sumUnit = "$",
            isClickOnSliceEnabled = false
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Expense Summary",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )

                PieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    pieChartData = pieChartData,
                    pieChartConfig = pieChartConfig
                ) {}

                Text(
                    text = "Top Expenses",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TopExpensesList(topExpenses = slices.sortedByDescending { it.value }.take(3))
            }
        }
    }
}
fun preparePieChartSlices(expenses: List<Expense>): List<PieChartData.Slice> {
    val categories = CategoryRepository.getCategories()

    val categoryMap = categories.associateBy { it.name }

    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { (_, expenses) -> expenses.sumOf { it.amt } }

    return categoryTotals.mapNotNull { (categoryName, totalAmount) ->
        if (totalAmount > 0) {
            val category = categoryMap[categoryName]

            PieChartData.Slice(
                label = categoryName,
                value = totalAmount.toFloat(),
                color = category?.color ?: Color.Gray
            )
        } else {
            null
        }
    }
}

@Composable
fun TopExpensesList(topExpenses: List<PieChartData.Slice>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        topExpenses.forEach { slice ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = slice.label, fontSize = 16.sp)
                Text(
                    text = "$${slice.value.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
