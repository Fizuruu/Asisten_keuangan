package com.example.asistenkeuangan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText etAmount, etNote, etDate;
    private Spinner spinnerCategory;
    private TabLayout tabLayoutType;
    private Button btnSave, btnDelete;
    private Calendar calendar = Calendar.getInstance();
    private TransactionViewModel viewModel;
    
    private int transactionId = -1; // -1 berarti mode Tambah Baru
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        initViews();
        setupToolbar();
        setupSpinner();
        setupDatePicker();
        checkIntentData();

        btnSave.setOnClickListener(v -> saveTransaction());
        btnDelete.setOnClickListener(v -> deleteTransaction());
    }

    private void initViews() {
        etAmount = findViewById(R.id.et_amount);
        etNote = findViewById(R.id.et_note);
        etDate = findViewById(R.id.et_date);
        spinnerCategory = findViewById(R.id.spinner_category);
        tabLayoutType = findViewById(R.id.tab_layout_type);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupSpinner() {
        String[] categories = {"Makan 🍱", "Transportasi 🚌", "Belanja 🛍️", "Gaji 💰", "Hiburan 🎮", "Lainnya ✨"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupDatePicker() {
        updateLabel();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        etDate.setOnClickListener(v -> new DatePickerDialog(AddTransactionActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void checkIntentData() {
        if (getIntent().hasExtra("EXTRA_ID")) {
            isEditMode = true;
            transactionId = getIntent().getIntExtra("EXTRA_ID", -1);
            etAmount.setText(String.valueOf(getIntent().getDoubleExtra("EXTRA_AMOUNT", 0)));
            etNote.setText(getIntent().getStringExtra("EXTRA_NOTE"));
            
            String type = getIntent().getStringExtra("EXTRA_TYPE");
            if ("income".equals(type)) {
                tabLayoutType.selectTab(tabLayoutType.getTabAt(1));
            } else {
                tabLayoutType.selectTab(tabLayoutType.getTabAt(0));
            }

            String category = getIntent().getStringExtra("EXTRA_CATEGORY");
            if (category != null) {
                ArrayAdapter myAdapter = (ArrayAdapter) spinnerCategory.getAdapter();
                int spinnerPosition = myAdapter.getPosition(category);
                spinnerCategory.setSelection(spinnerPosition);
            }

            calendar.setTimeInMillis(getIntent().getLongExtra("EXTRA_DATE", System.currentTimeMillis()));
            updateLabel();

            btnDelete.setVisibility(View.VISIBLE);
            btnSave.setText("UPDATE TRANSAKSI ✨");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Edit Transaksi ✨");
            }
        }
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void saveTransaction() {
        String amountStr = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        
        if (amountStr.isEmpty()) {
            etAmount.setError("Masukkan jumlah uang");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String type = (tabLayoutType.getSelectedTabPosition() == 1) ? "income" : "expense";
        long date = calendar.getTimeInMillis();

        Transaction transaction = new Transaction(amount, type, category, note, date);
        
        if (isEditMode) {
            transaction.setId(transactionId);
            viewModel.update(transaction);
            Toast.makeText(this, "Transaksi diperbarui", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insert(transaction);
            Toast.makeText(this, "Transaksi disimpan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteTransaction() {
        Transaction transaction = new Transaction(0, "", "", "", 0);
        transaction.setId(transactionId);
        viewModel.delete(transaction);
        Toast.makeText(this, "Transaksi dihapus", Toast.LENGTH_SHORT).show();
        finish();
    }
}
