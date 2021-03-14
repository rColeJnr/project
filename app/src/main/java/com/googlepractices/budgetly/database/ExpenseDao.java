package com.googlepractices.budgetly.database;

import androidx.room.Query;
import androidx.room.Dao;

import com.googlepractices.budgetly.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("INSERT INTO expenses (month, day, year, amount, category) VALUES (:m, :d, :y, :amount, :category)")
    void create(int m, int d, int y, float amount, String category);

    @Query("SELECT * FROM expenses WHERE month = :m AND day = :d AND year = :y")
    List<Expense> selectDay(int m, int d, int y);

    @Query("SELECT * FROM expenses WHERE month = :m")
    List<Expense> selectMonth(int m);

    @Query("SELECT * FROM expenses ORDER BY day, month, year")
    List<Expense> selectAll();
}
