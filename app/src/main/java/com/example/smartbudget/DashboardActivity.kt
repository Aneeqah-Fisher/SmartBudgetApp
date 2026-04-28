package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = AppDatabase.getDatabase(this)

        loadExpenses()
        Log.d("Dashboard", "Dashboard opened")
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    private fun loadExpenses() {

        val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
        val currentUser = prefs.getString("loggedInUser", "") ?: ""

        val expenses = db.expenseDao().getExpensesForUser(currentUser)
        recyclerView.adapter = ExpenseAdapter(expenses)

        val total = db.expenseDao().getTotalForUser(currentUser) ?: 0.0
        val txtTotal = findViewById<TextView>(R.id.txtTotal)
        txtTotal.text = String.format("R %.2f", total)

        val categoryTotals = db.expenseDao().getCategoryTotals(currentUser)
        val txtCategoryTotals = findViewById<TextView>(R.id.txtCategoryTotals)

        val builder = StringBuilder()
        for (item in categoryTotals) {
            builder.append("${item.category}: R ${"%.2f".format(item.total)}\n")

            Log.d("Dashboard", "Loading expenses")
        }

        txtCategoryTotals.text = builder.toString()

        val minGoal = prefs.getFloat("minGoal", 0f)
        val maxGoal = prefs.getFloat("maxGoal", 0f)
        val txtGoals = findViewById<TextView>(R.id.txtGoals)

        val status = when {
            minGoal == 0f && maxGoal == 0f -> "No goals set"
            total < minGoal -> "Below minimum goal"
            total > maxGoal -> "Over budget"
            else -> "Within budget"
        }

        val startDateInput = findViewById<EditText>(R.id.editStartDate)
        val endDateInput = findViewById<EditText>(R.id.editEndDate)
        val btnFilter = findViewById<Button>(R.id.btnFilter)

        btnFilter.setOnClickListener {
            val startDate = startDateInput.text.toString().trim()
            val endDate = endDateInput.text.toString().trim()

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Enter start and end dates", Toast.LENGTH_SHORT).show()
            } else {
                filterExpenses(startDate, endDate)
            }
        }

        btnFilter.setOnClickListener {
            val startDate = startDateInput.text.toString()
            val endDate = endDateInput.text.toString()

            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                filterExpenses(startDate, endDate)

                Log.d("Filter", "Filtering from $startDate to $endDate")
                Log.d("Filter", "Filtered results: ${expenses.size}")
            }
        }

        txtGoals.text = """
Minimum Goal: R %.2f
Maximum Goal: R %.2f
Status: $status
""".trimIndent().format(minGoal, maxGoal)
    }

    fun goToAddExpense(view: View) {
        startActivity(Intent(this, AddExpenseActivity::class.java))
    }

    fun goToGoals(view: View) {
        startActivity(Intent(this, GoalsActivity::class.java))
    }

    private fun filterExpenses(startDate: String, endDate: String) {
            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            val currentUser = prefs.getString("loggedInUser", "") ?: ""

            try {
                val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                val dbFormat = java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault())

                val start = dbFormat.format(inputFormat.parse(startDate)!!)
                val end = dbFormat.format(inputFormat.parse(endDate)!!)

                val expenses = db.expenseDao().getExpensesForUserByDate(currentUser, start, end)
                recyclerView.adapter = ExpenseAdapter(expenses)

                val txtTotal = findViewById<TextView>(R.id.txtTotal)
                val filteredTotal = expenses.sumOf { it.amount }
                txtTotal.text = String.format("R %.2f", filteredTotal)

                val txtCategoryTotals = findViewById<TextView>(R.id.txtCategoryTotals)
                val categoryText = expenses
                    .groupBy { it.category }
                    .map { (category, list) ->
                        val sum = list.sumOf { it.amount }
                        "$category: R %.2f".format(sum)
                    }
                    .joinToString("\n")

                txtCategoryTotals.text =
                    if (categoryText.isEmpty()) "No expenses found for this period" else categoryText

                Toast.makeText(this, "${expenses.size} expenses found", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show()
            }
        }

    }
