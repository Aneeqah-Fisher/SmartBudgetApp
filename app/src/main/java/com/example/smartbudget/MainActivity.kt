package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.editUsername)
        val password = findViewById<EditText>(R.id.editPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val signupText = findViewById<TextView>(R.id.txtSignup)

        btnLogin.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            val savedUsername = prefs.getString("username", "")
            val savedPassword = prefs.getString("password", "")

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else if (user == savedUsername && pass == savedPassword) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
                val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
                prefs.edit().putString("loggedInUser", user).apply()
            } else {
                Toast.makeText(this, "Invalid login details", Toast.LENGTH_SHORT).show()
            }

        }

        signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}