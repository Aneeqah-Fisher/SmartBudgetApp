package com.example.smartbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
        val amount: TextView = view.findViewById(R.id.txtAmount)
        val category: TextView = view.findViewById(R.id.txtCategory)
        val date: TextView = view.findViewById(R.id.txtDate)
        val photoStatus: TextView = view.findViewById(R.id.txtPhotoStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = expenses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses[position]

        holder.title.text = expense.title
        holder.amount.text = String.format("Amount: R %.2f", expense.amount)
        holder.category.text = "Category: ${expense.category}"
        holder.date.text = "Date: ${expense.displayDate}"
        if (expense.imageUri != null) {
            holder.photoStatus.text = "Receipt attached - tap to view"

            holder.photoStatus.setOnClickListener {
                val intent = Intent(holder.itemView.context, ReceiptActivity::class.java)
                intent.putExtra("imageUri", expense.imageUri)
                holder.itemView.context.startActivity(intent)
            }

        } else {
            holder.photoStatus.text = "No receipt attached"
        }
    }
}