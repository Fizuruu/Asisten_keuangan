package com.example.asistenkeuangan;

/**
 * Class bantu untuk menampung hasil query grouping per kategori.
 */
public class CategorySum {
    public String category;
    public double total;

    public CategorySum(String category, double total) {
        this.category = category;
        this.total = total;
    }
}
