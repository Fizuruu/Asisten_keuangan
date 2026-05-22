package com.example.asistenkeuangan;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {
    private TextView tvMonthYear, tvIncome, tvExpense, tvNet;
    private TransactionViewModel viewModel;
    private double currentIncome = 0;
    private double currentExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_summary);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        tvMonthYear = findViewById(R.id.tv_month_year);
        tvIncome = findViewById(R.id.tv_summary_income);
        tvExpense = findViewById(R.id.tv_summary_expense);
        tvNet = findViewById(R.id.tv_summary_net);

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("id", "ID"));
        tvMonthYear.setText(sdf.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long startOfMonth = cal.getTimeInMillis();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        long endOfMonth = cal.getTimeInMillis();

        viewModel.getTotalIncomeByMonth(startOfMonth, endOfMonth).observe(this, income -> {
            currentIncome = (income != null) ? income : 0;
            updateSummaryUI();
        });

        viewModel.getTotalExpenseByMonth(startOfMonth, endOfMonth).observe(this, expense -> {
            currentExpense = (expense != null) ? expense : 0;
            updateSummaryUI();
        });
    }

    private void updateSummaryUI() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        tvIncome.setText(currencyFormat.format(currentIncome));
        tvExpense.setText(currencyFormat.format(currentExpense));
        tvNet.setText(currencyFormat.format(currentIncome - currentExpense));
    }
}
