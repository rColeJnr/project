package com.googlepractices.budgetly.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.googlepractices.budgetly.R;
import com.googlepractices.budgetly.ui.BudgetLYActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class StatisticsFragment extends Fragment {

    TextView tvR, tvPython, tvCPP, tvJava;
    PieChart pieChart;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Link those objects with their
        // respective id's that
        // we have given in .XML file
        pieChart = getActivity().findViewById(R.id.pie_chart);

        // Creating a method setData()
        // to set the text in text view and pie chart
        try {
            setData();
        }
        catch (NullPointerException e) {
            Log.v("Exception", e.toString());
        }
    }

    private void setData()
    {
        Log.v("db", String.valueOf(BudgetLYActivity.database.expenseDao().selectCategoryMonth("Food", "3")));
        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Food",
                        75,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Entertainment",
                        25,
                        Color.parseColor("#29B6F6")));
        // To animate the pie chart
        pieChart.startAnimation();
    }

    private float getTotalCategory(String category) {
        return BudgetLYActivity.database.expenseDao().selectCategoryMonth(category, "3");
    }

    private float getTotal() {
        return BudgetLYActivity.database.expenseDao().selectTotal();
    }



}