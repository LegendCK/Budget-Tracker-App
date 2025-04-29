package com.example.budgettrackerapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.budgettrackerapp.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Transactions : Screen("transactions")
    object AddTransaction : Screen("add_transaction")
    object Categories : Screen("categories")
    object Settings : Screen("settings")
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Transactions.route) { TransactionsScreen() }
            composable(Screen.AddTransaction.route) { AddTransactionScreen() }
            composable(Screen.Categories.route) { CategoriesScreen() }
            composable(Screen.Settings.route) { SettingsScreen(context = LocalContext.current) }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Transactions,
        Screen.AddTransaction,
        Screen.Categories,
        Screen.Settings
    )

    NavigationBar {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.route,
                onClick = {
                    if (currentDestination != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    when (screen) {
                        is Screen.Home -> Icon(Icons.Default.Home, contentDescription = "Home")
                        is Screen.Transactions -> Icon(Icons.Default.List, contentDescription = "Transactions")
                        is Screen.AddTransaction -> Icon(Icons.Default.Add, contentDescription = "Add")
                        is Screen.Categories -> Icon(Icons.Default.ShoppingCart, contentDescription = "Categories")
                        is Screen.Settings -> Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                label = { Text(screen.route.replace('_', ' ').replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}
