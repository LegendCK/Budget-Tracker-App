package com.example.budgettrackerapp.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.preference.PreferenceManager
import com.example.budgettrackerapp.utils.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    context: Context = LocalContext.current,
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit
) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val savedCurrency = sharedPreferences.getString("currency", "₹") ?: "₹"
    var selectedCurrency by remember { mutableStateOf(savedCurrency) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Settings", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Currency", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        CurrencyDropDownMenu(selectedCurrency) { currency ->
            selectedCurrency = currency
            sharedPreferences.edit().putString("currency", currency).apply()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Theme", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        ThemeRadioGroup(currentTheme) { selectedAppTheme ->
            onThemeChange(selectedAppTheme)
        }
    }
}

@Composable
fun ThemeRadioGroup(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    val themes = listOf(AppTheme.Light, AppTheme.Dark, AppTheme.System)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        themes.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onThemeSelected(theme) }
            ) {
                RadioButton(
                    selected = selectedTheme == theme,
                    onClick = { onThemeSelected(theme) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = theme.name)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDownMenu(selectedCurrency: String, onCurrencySelected: (String) -> Unit) {
    val currencyOptions = listOf("₹", "$", "€", "£")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedCurrency,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Currency") },
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencyOptions.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}