package com.googlepractices.budgetly.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.googlepractices.budgetly.model.Expense;
import com.googlepractices.budgetly.R;
import com.googlepractices.budgetly.ui.BudgetLYActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView expenseDate;
        public TextView expenseAmount;
        public TextView expenseCategory;

        public ViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.container_layout);
            this.expenseDate = view.findViewById(R.id.expense_date);
            this.expenseAmount = view.findViewById(R.id.expense_amount);
            this.expenseCategory = view.findViewById(R.id.expense_category);
        }
    }

    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense current = expenses.get(position);
        holder.containerView.setTag(current);
        holder.expenseDate.setText(String.format("%s %s, %s", current.month, current.day, current.year));
        holder.expenseAmount.setText(String.valueOf(current.amount));
        holder.expenseCategory.setText(current.category);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void load() {
        expenses = BudgetLYActivity.database.expenseDao().selectAll();
        notifyDataSetChanged();
    }
}
