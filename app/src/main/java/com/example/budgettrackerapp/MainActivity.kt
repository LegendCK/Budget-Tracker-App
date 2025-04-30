package com.example.budgettrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
import com.example.budgettrackerapp.navigation.MainScreen
import com.example.budgettrackerapp.ui.theme.BudgetTrackerAppTheme
import com.example.budgettrackerapp.utils.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val savedTheme = when (sharedPrefs.getString("theme", "System")) {
            "Light" -> AppTheme.Light
            "Dark" -> AppTheme.Dark
            else -> AppTheme.System
        }

        setContent {
            var appTheme by remember { mutableStateOf(savedTheme) }

            val isDarkTheme = when (appTheme) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            BudgetTrackerAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController,
                    currentTheme = appTheme,
                    onThemeChange = { newTheme ->
                        appTheme = newTheme
                        sharedPrefs.edit().putString("theme", newTheme.name).apply()
                    }
                )
            }
        }
    }
}
