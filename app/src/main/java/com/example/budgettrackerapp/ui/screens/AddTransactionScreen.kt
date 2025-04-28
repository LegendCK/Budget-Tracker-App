package com.example.budgettrackerapp.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgettrackerapp.data.model.Expense
import com.example.budgettrackerapp.data.model.Income
import com.example.budgettrackerapp.data.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddTransactionScreen(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Transaction") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Income") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Expense") }
                )
            }

            when (selectedTabIndex) {
                0 -> {
                    IncomeForm()
                }

                1 -> {
                    ExpenseForm()
                }
            }
        }
    }
}

@Composable
fun IncomeForm() {
    val currentDate = LocalDate.now()
    val context = LocalContext.current

    var amt by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }
    var openDatePicker by rememberSaveable { mutableStateOf(false) }

    val isFormValid = amt.isNotBlank() && desc.isNotBlank() && date.isNotBlank()
    val coroutineScope = rememberCoroutineScope()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth) // Month is 0-based
                date = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                openDatePicker = false
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        )
    }

    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TextField(
            value = amt,
            onValueChange = { amt = it },
            leadingIcon = { Text("₹") },
            placeholder = { Text("0.00") },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Description",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter description") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Date",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Select Date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                    openDatePicker = true
                }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val income = Income(amt.toDouble(), desc, date)
                        TransactionRepository.addIncome(income)

                        Toast.makeText(context, "Income Added: ${desc} - ₹${amt} - Date: $date", Toast.LENGTH_SHORT).show()

                        amt = ""
                        desc = ""
                        date = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error adding income: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun ExpenseForm() {
    val currentDate = LocalDate.now()
    val context = LocalContext.current
    var amt by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }
    var openDatePicker by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf("") }

    val categories = listOf("Food", "Transport", "Bills", "Entertainment", "Groceries", "Health", "Shopping", "Misc")
    val isFormValid = amt.isNotBlank() && desc.isNotBlank() && date.isNotBlank() && selectedCategory.isNotBlank()
    val coroutineScope = rememberCoroutineScope()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth) // Month is 0-based
                date = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                openDatePicker = false
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        )
    }

    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TextField(
            value = amt,
            onValueChange = { amt = it },
            leadingIcon = { Text("₹") },
            placeholder = { Text("0.00") },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Description",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter description") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Category",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .clickable {
                            selectedCategory = category
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (selectedCategory == category) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Date",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Select Date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                    openDatePicker = true
                }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val expense = Expense(
                            amt.toDouble(),
                            desc,
                            date,
                            selectedCategory
                        )
                        TransactionRepository.addExpense(expense)
                        Toast.makeText(
                            context,
                            "Expense Added: ${desc} - ₹${amt} - Category: $selectedCategory - Date: $date",
                            Toast.LENGTH_SHORT
                        ).show()
                        amt = ""
                        desc = ""
                        date = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        selectedCategory = ""
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error adding expense: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Submit")
        }
    }
}
