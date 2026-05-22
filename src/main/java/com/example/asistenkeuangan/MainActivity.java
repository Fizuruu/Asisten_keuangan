package com.example.asistenkeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TransactionViewModel transactionViewModel;
    private TextView tvTotalBalance;
    private double totalIncome = 0;
    private double totalExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalBalance = findViewById(R.id.tv_total_balance);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        
        transactionViewModel.getAllTransactions().observe(this, adapter::setTransactions);

        transactionViewModel.getTotalIncome().observe(this, income -> {
            totalIncome = (income != null) ? income : 0;
            updateBalanceText();
        });

        transactionViewModel.getTotalExpense().observe(this, expense -> {
            totalExpense = (expense != null) ? expense : 0;
            updateBalanceText();
        });

        // Handle Click untuk Edit
        adapter.setOnItemClickListener(transaction -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            intent.putExtra("EXTRA_ID", transaction.getId());
            intent.putExtra("EXTRA_AMOUNT", transaction.getAmount());
            intent.putExtra("EXTRA_TYPE", transaction.getType());
            intent.putExtra("EXTRA_CATEGORY", transaction.getCategory());
            intent.putExtra("EXTRA_NOTE", transaction.getNote());
            intent.putExtra("EXTRA_DATE", transaction.getDate());
            startActivity(intent);
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_summary).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
        });
    }

    private void updateBalanceText() {
        double balance = totalIncome - totalExpense;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        tvTotalBalance.setText(currencyFormat.format(balance));
    }
}
