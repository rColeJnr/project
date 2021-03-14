package com.googlepractices.budgetly.database;

import androidx.room.Query;
import androidx.room.Dao;

import com.googlepractices.budgetly.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("INSERT INTO expenses (month, day, year, amount, category) VALUES (:m, :d, :y, :amount, :category)")
    void create(String m, String d, String y, float amount, String category);

    @Query("SELECT * FROM expenses WHERE month = :m AND day = :d AND year = :y")
    List<Expense> selectDay(String m, String d, String y);

    @Query("SELECT * FROM expenses WHERE month = :m")
    List<Expense> selectMonth(String m);

    @Query("SELECT * FROM expenses ORDER BY day, month, year")
    List<Expense> selectAll();

    @Query("SELECT SUM(amount) FROM expenses WHERE category = :category AND month = :m")
    float selectCategoryMonth(String category, String m);

    @Query("SELECT SUM(amount) FROM expenses")
    float selectTotal();
}
