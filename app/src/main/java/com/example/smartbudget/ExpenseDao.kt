package com.example.smartbudget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: Expense)

    @Query("SELECT * FROM expenses WHERE username = :user ORDER BY id DESC")
    fun getExpensesForUser(user: String): List<Expense>

    @Query("SELECT SUM(amount) FROM expenses WHERE username = :user")
    fun getTotalForUser(user: String): Double?

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE username = :user GROUP BY category")
    fun getCategoryTotals(user: String): List<CategoryTotal>

    @Query("SELECT * FROM expenses WHERE username = :user AND date BETWEEN :startDate AND :endDate ORDER BY id DESC")
    fun getExpensesForUserByDate(user: String, startDate: String, endDate: String): List<Expense>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE username = :user AND date BETWEEN :startDate AND :endDate GROUP BY category")
    fun getCategoryTotalsByDate(user: String, startDate: String, endDate: String): List<CategoryTotal>
}