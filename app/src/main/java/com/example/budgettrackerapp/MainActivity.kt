package com.example.budgettrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.budgettrackerapp.ui.screens.AddTransactionScreen
import com.example.budgettrackerapp.ui.theme.BudgetTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val db = Firebase.firestore
//        val test = hashMapOf("hello" to "world")
//        db.collection("test").add(test)
//            .addOnSuccessListener { Log.d("Firestore", "Success") }
//            .addOnFailureListener { Log.d("Firestore", "Failed") }

        setContent {
            BudgetTrackerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddTransactionScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
