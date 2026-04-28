package com.example.smartbudget

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val imageView = findViewById<ImageView>(R.id.imgFullReceipt)
        val imageUriString = intent.getStringExtra("imageUri")

        if (imageUriString.isNullOrEmpty()) {
            Toast.makeText(this, "No receipt image found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        try {
            val uri = Uri.parse(imageUriString)
            imageView.setImageURI(uri)
        } catch (e: Exception) {
            Toast.makeText(this, "Could not open receipt image", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}