package com.example.smartbudget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val username = findViewById<EditText>(R.id.signupUsername)
        val password = findViewById<EditText>(R.id.signupPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            prefs.edit()
                .putString("username", user)
                .putString("password", pass)
                .apply()

            Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}