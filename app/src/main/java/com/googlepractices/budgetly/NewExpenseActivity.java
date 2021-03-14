package com.googlepractices.budgetly;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.googlepractices.budgetly.ui.BudgetLYActivity;

public class NewExpenseActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Button saveExpenseButton;
    private EditText mEditText;
    private EditText dEditText;
    private EditText yEditText;
    private Spinner categorySpinner;

    public void saveEvent() {
        try {
            BudgetLYActivity.database.expenseDao().create(mEditText.getText().toString(), dEditText.getText().toString(), yEditText.getText().toString(), Float.parseFloat(amountEditText.getText().toString()), categorySpinner.getSelectedItem().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            Log.v("error", "could't add new event");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newexpense);

        amountEditText = findViewById(R.id.name_edit_text);
        saveExpenseButton = findViewById(R.id.save_event_button);
        mEditText = findViewById(R.id.m_edit_text);
        dEditText = findViewById(R.id.d_edit_text);
        yEditText = findViewById(R.id.y_edit_text);
        categorySpinner = findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        saveExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent();
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveEvent();
    }
}