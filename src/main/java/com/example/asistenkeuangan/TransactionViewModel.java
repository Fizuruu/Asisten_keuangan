package com.example.asistenkeuangan;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository mRepository;
    private final LiveData<List<Transaction>> mAllTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Double> getTotalIncome() {
        return mRepository.getTotalIncome();
    }

    public LiveData<Double> getTotalExpense() {
        return mRepository.getTotalExpense();
    }

    public LiveData<Double> getTotalIncomeByMonth(long start, long end) {
        return mRepository.getTotalIncomeByMonth(start, end);
    }

    public LiveData<Double> getTotalExpenseByMonth(long start, long end) {
        return mRepository.getTotalExpenseByMonth(start, end);
    }

    public LiveData<List<CategorySum>> getCategorySum(String type, long start, long end) {
        return mRepository.getCategorySum(type, start, end);
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }

    public void update(Transaction transaction) {
        mRepository.update(transaction);
    }

    public void delete(Transaction transaction) {
        mRepository.delete(transaction);
    }
}
