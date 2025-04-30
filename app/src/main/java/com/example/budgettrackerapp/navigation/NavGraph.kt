package com.example.budgettrackerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgettrackerapp.ui.screens.*
import com.example.budgettrackerapp.utils.AppTheme

@Composable
fun NavGraph(
    navController: NavHostController,
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(modifier = modifier)
        }
        composable("transactions") {
            TransactionsScreen(modifier = modifier)
        }
        composable("add_transaction") {
            AddTransactionScreen(modifier = modifier)
        }
        composable("categories") {
            CategoriesScreen(modifier = modifier)
        }
        composable("settings") {
            SettingsScreen(
                context = context,
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }
    }
}
