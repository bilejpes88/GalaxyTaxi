package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExpensesActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_expenses)

		val lpgInput = findViewById<EditText>(R.id.lpgAmountInput)
		val n95Input = findViewById<EditText>(R.id.n95AmountInput)
		val otherExpensesInput = findViewById<EditText>(R.id.otherExpensesInput)
		val descriptionInput = findViewById<EditText>(R.id.expenseDescriptionInput)
		val okButton = findViewById<Button>(R.id.okButton)
		val backButton = findViewById<Button>(R.id.backButton)

		okButton.setOnClickListener {
			val lpgAmount = lpgInput.text.toString()
			val n95Amount = n95Input.text.toString()
			val otherAmount = otherExpensesInput.text.toString()
			val description = descriptionInput.text.toString()

			// Validace
			if (lpgAmount.isEmpty() && n95Amount.isEmpty() && otherAmount.isEmpty()) {
				Toast.makeText(this, "Zadejte alespoň jednu částku", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			Toast.makeText(this, "Výdaje byly uloženy", Toast.LENGTH_SHORT).show()

			// Přechod zpět na hlavní obrazovku směny
			val intent = Intent(this, MainShiftActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			finish()
		}

		// Akce tlačítka Zpět
		backButton.setOnClickListener {
			val intent = Intent(this, MainShiftActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			finish()
		}
	}
}
