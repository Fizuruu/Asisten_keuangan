package com.example.asistenkeuangan;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TransactionRepository {
    private TransactionDao mTransactionDao;
    private LiveData<List<Transaction>> mAllTransactions;

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTransactionDao = db.transactionDao();
        mAllTransactions = mTransactionDao.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Double> getTotalIncome() {
        return mTransactionDao.getTotalIncome();
    }

    public LiveData<Double> getTotalExpense() {
        return mTransactionDao.getTotalExpense();
    }

    public LiveData<Double> getTotalIncomeByMonth(long start, long end) {
        return mTransactionDao.getTotalByTypeAndMonth("income", start, end);
    }

    public LiveData<Double> getTotalExpenseByMonth(long start, long end) {
        return mTransactionDao.getTotalByTypeAndMonth("expense", start, end);
    }

    public LiveData<List<CategorySum>> getCategorySum(String type, long start, long end) {
        return mTransactionDao.getCategorySum(type, start, end);
    }

    public void insert(Transaction transaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransactionDao.insert(transaction);
        });
    }

    public void update(Transaction transaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransactionDao.update(transaction);
        });
    }

    public void delete(Transaction transaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransactionDao.delete(transaction);
        });
    }
}
