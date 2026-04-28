package com.example.smartbudget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GoalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        val minGoal = findViewById<EditText>(R.id.editMinGoal)
        val maxGoal = findViewById<EditText>(R.id.editMaxGoal)
        val btnSave = findViewById<Button>(R.id.btnSaveGoals)

        btnSave.setOnClickListener {
            val min = minGoal.text.toString().toFloatOrNull()
            val max = maxGoal.text.toString().toFloatOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Enter valid goals", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (min > max) {
                Toast.makeText(this, "Minimum cannot be more than maximum", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            prefs.edit()
                .putFloat("minGoal", min)
                .putFloat("maxGoal", max)
                .apply()

            Toast.makeText(this, "Goals saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}