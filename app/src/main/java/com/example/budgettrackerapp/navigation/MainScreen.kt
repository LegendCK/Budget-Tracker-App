package com.example.budgettrackerapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.composables.icons.lucide.CirclePlus
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Layers
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Receipt
import com.composables.icons.lucide.Settings
import com.example.budgettrackerapp.ui.screens.AddTransactionScreen
import com.example.budgettrackerapp.ui.screens.CategoriesScreen
import com.example.budgettrackerapp.ui.screens.HomeScreen
import com.example.budgettrackerapp.ui.screens.SettingsScreen
import com.example.budgettrackerapp.ui.screens.TransactionsScreen
import com.example.budgettrackerapp.utils.AppTheme

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Lucide.House)
    object Transactions : Screen("transactions", "Transaction", Lucide.Receipt)
    object AddTransaction : Screen("add_transaction", "Add", Lucide.CirclePlus)
    object Categories : Screen("categories", "Categories", Lucide.Layers)
    object Settings : Screen("settings", "Settings", Lucide.Settings)
}

@Composable
fun MainScreen(
    navController: NavHostController,
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit
) {
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
            composable(Screen.Settings.route) {
                SettingsScreen(
                    context = LocalContext.current,
                    currentTheme = currentTheme,
                    onThemeChange = onThemeChange
                )
            }
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

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
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
                    Icon(screen.icon, contentDescription = screen.label)
                },
                label = {
                    Text(text = screen.label)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                )
            )
        }
    }
}
