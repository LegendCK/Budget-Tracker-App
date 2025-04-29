package com.example.budgettrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.budgettrackerapp.navigation.MainScreen
import com.example.budgettrackerapp.ui.theme.BudgetTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetTrackerAppTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

