package com.example.galaxytaxi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpensesAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
		return ExpenseViewHolder(view)
	}

	override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
		val expense = expenses[position]
		holder.descriptionText.text = expense.description
		holder.amountText.text = "%.2f Kč".format(expense.amount)
	}

	override fun getItemCount() = expenses.size

	inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
		val amountText: TextView = itemView.findViewById(R.id.amountText)
	}
}
