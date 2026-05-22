package com.example.asistenkeuangan;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'income'")
    LiveData<Double> getTotalIncome();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense'")
    LiveData<Double> getTotalExpense();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date >= :start AND date <= :end")
    LiveData<Double> getTotalByTypeAndMonth(String type, long start, long end);

    @Query("SELECT category, SUM(amount) as total FROM transactions WHERE type = :type AND date >= :start AND date <= :end GROUP BY category")
    LiveData<List<CategorySum>> getCategorySum(String type, long start, long end);
}
