package com.example.budgettrackerapp.data.repository

import com.example.budgettrackerapp.data.model.Expense
import com.example.budgettrackerapp.data.model.Income
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TransactionRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addIncome(income: Income) {
        try {
            db.collection("income")
                .add(income)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addExpense(expense: Expense) {
        try {
            db.collection("expenses")
                .add(expense)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getIncome(): List<Income> {
        return try {
            db.collection("income")
                .get()
                .await()
                .toObjects(Income::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getExpenses(): List<Expense> {
        return try {
            db.collection("expenses")
                .get()
                .await()
                .toObjects(Expense::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
