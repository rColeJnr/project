package com.googlepractices.budgetly.model;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey
    public int id;

    @ColumnInfo(name="month")
    public String month;

    @ColumnInfo(name="day")
    public String day;

    @ColumnInfo(name="year")
    public String year;

    @ColumnInfo(name="amount")
    public float amount;

    @ColumnInfo(name="category", defaultValue = "None")
    public String category;
}
