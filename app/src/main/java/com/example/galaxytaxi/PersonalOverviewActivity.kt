package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PersonalOverviewActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_personal_overview)

		val ridesRecyclerView = findViewById<RecyclerView>(R.id.ridesRecyclerView)
		val expensesRecyclerView = findViewById<RecyclerView>(R.id.expensesRecyclerView)
		val totalIncomeText = findViewById<TextView>(R.id.totalIncomeText)
		val totalExpensesText = findViewById<TextView>(R.id.totalExpensesText)
		val totalDueText = findViewById<TextView>(R.id.totalDueText)
		val overallDueText = findViewById<TextView>(R.id.overallDueText)
		val endShiftButton = findViewById<Button>(R.id.endShiftButton)

		val rides = getRidesFromDatabase()
		val expenses = getExpensesFromDatabase()

		val ridesAdapter = RidesAdapter(rides)
		ridesRecyclerView.layoutManager = LinearLayoutManager(this)
		ridesRecyclerView.adapter = ridesAdapter

		val expensesAdapter = ExpensesAdapter(expenses)
		expensesRecyclerView.layoutManager = LinearLayoutManager(this)
		expensesRecyclerView.adapter = expensesAdapter

		val totalIncome = rides.sumOf { it.price }
		val totalExpenses = expenses.sumOf { it.amount }
		val totalDue = (2.0 / 3.0) * totalIncome - totalExpenses

		totalIncomeText.text = "Utržená částka: %.2f Kč".format(totalIncome)
		totalExpensesText.text = "Výdaje: %.2f Kč".format(totalExpenses)
		totalDueText.text = "Odvod: %.2f Kč".format(totalDue)
		overallDueText.text = "Odvod celkem: %.2f Kč".format(totalDue)

		endShiftButton.setOnClickListener {
			val intent = Intent(this, FinalMileageActivity::class.java)
			startActivity(intent)
		}
	}

	private fun getRidesFromDatabase(): List<ShiftRide> {
		return DatabaseHelper.getInstance(this).getAllRides()
	}

	private fun getExpensesFromDatabase(): List<Expense> {
		return DatabaseHelper.getInstance(this).getAllExpenses()
	}
}
