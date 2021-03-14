package com.googlepractices.budgetly.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.googlepractices.budgetly.model.Expense;

@Database(entities = {Expense.class}, version = 1)
public abstract class ExpensesDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
}
