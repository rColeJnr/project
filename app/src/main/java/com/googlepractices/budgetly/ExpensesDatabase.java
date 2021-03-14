package com.googlepractices.budgetly;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1)
public abstract class ExpensesDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
}
