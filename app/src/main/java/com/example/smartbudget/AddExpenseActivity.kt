package com.example.smartbudget

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.content.Intent
import android.util.Log

class AddExpenseActivity : AppCompatActivity() {

    private var imageUri: String? = null

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            if (uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imageUri = uri.toString()
                Toast.makeText(this, "Receipt attached", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val titleInput = findViewById<EditText>(R.id.editTitle)
        val amountInput = findViewById<EditText>(R.id.editAmount)
        val categoryInput = findViewById<EditText>(R.id.editCategory)
        val btnPhoto = findViewById<Button>(R.id.btnPhoto)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val db = AppDatabase.getDatabase(this)

        btnPhoto.setOnClickListener {
            pickImage.launch(arrayOf("image/*"))

        }

        btnSave.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val amountText = amountInput.text.toString().trim()
            val category = categoryInput.text.toString().trim()

            if (title.isEmpty() || amountText.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            val currentUser = prefs.getString("loggedInUser", "") ?: ""

            val filterFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val displayFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val dateForFilter = filterFormat.format(Date())
            val dateForDisplay = displayFormat.format(Date())

            Log.d("AddExpense", "User clicked save button")
            Log.d("AddExpense", "Saving expense: $title, R$amount, $category")
            Log.d("AddExpense", "Image URI: $imageUri")

            val expense = Expense(
                title = title,
                amount = amount,
                category = category,
                username = currentUser,
                date = dateForFilter,
                displayDate = dateForDisplay,
                imageUri = imageUri
            )

            db.expenseDao().insert(expense)

            Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}