package com.example.smartbudget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SavingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        val goalInput = findViewById<EditText>(R.id.editSavingsGoal)
        val btnSave = findViewById<Button>(R.id.btnSaveSavingsGoal)
        val txtGoal = findViewById<TextView>(R.id.txtSavingsProgress)
        val txtCurrentSavings = findViewById<TextView>(R.id.txtCurrentSavings)
        val txtBadge = findViewById<TextView>(R.id.txtSavingsBadge)
        val progressBar = findViewById<ProgressBar>(R.id.progressSavings)

        val prefs = getSharedPreferences("user_data", MODE_PRIVATE)

        val db = AppDatabase.getDatabase(this)

        val currentUser =
            prefs.getString("loggedInUser", "") ?: ""

        val totalSpent =
            db.expenseDao().getTotalForUser(currentUser) ?: 0.0

        val maxGoal =
            prefs.getFloat("maxGoal", 0f)

        val currentSavings =
            (maxGoal - totalSpent).coerceAtLeast(0.0)

        val savedGoal =
            prefs.getFloat("savingsGoal", 0f)

        if (savedGoal > 0) {

            goalInput.setText(savedGoal.toString())

            updateSavingsDisplay(
                savedGoal,
                currentSavings,
                txtGoal,
                txtCurrentSavings,
                txtBadge,
                progressBar
            )
        }

        btnSave.setOnClickListener {

            val goal =
                goalInput.text.toString().toFloatOrNull()

            if (goal == null || goal <= 0) {

                Toast.makeText(
                    this,
                    "Enter a valid savings goal",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            prefs.edit()
                .putFloat("savingsGoal", goal)
                .apply()

            updateSavingsDisplay(
                goal,
                currentSavings,
                txtGoal,
                txtCurrentSavings,
                txtBadge,
                progressBar
            )

            Toast.makeText(
                this,
                "Savings goal saved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateSavingsDisplay(
        goal: Float,
        currentSavings: Double,
        txtGoal: TextView,
        txtCurrentSavings: TextView,
        txtBadge: TextView,
        progressBar: ProgressBar
    ) {

        txtGoal.text =
            "Savings Goal: R %.2f".format(goal)

        txtCurrentSavings.text =
            "Current Savings: R %.2f".format(currentSavings)

        val percentage =
            ((currentSavings / goal) * 100).toInt()
                .coerceIn(0, 100)

        progressBar.max = 100
        progressBar.progress = percentage

        txtBadge.text = when {

            percentage >= 100 ->
                "💎 Savings Champion"

            percentage >= 75 ->
                "🥇 Gold Saver"

            percentage >= 50 ->
                "🥈 Silver Saver"

            percentage >= 25 ->
                "🥉 Bronze Saver"

            else ->
                "🚀 Getting Started"
        }
    }
}