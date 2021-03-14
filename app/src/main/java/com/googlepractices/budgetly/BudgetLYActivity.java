package com.googlepractices.budgetly;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BudgetLYActivity extends AppCompatActivity {
    public static ExpensesDatabase database;
    public BottomNavigationView bottomNavigationView;
    public Fragment expensesFragment = new ExpensesFragment();
    public Fragment statisticsFragment = new StatisticsFragment();
    public Fragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        // Pass the ID's of all the fragments
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_expenses, R.id.fragment_statistics, R.id.fragment_settings)
                .build();

        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Instantiate database
        database = Room.databaseBuilder(getApplicationContext(), ExpensesDatabase.class, "expenses")
                .allowMainThreadQueries()
                .build();
    }
}