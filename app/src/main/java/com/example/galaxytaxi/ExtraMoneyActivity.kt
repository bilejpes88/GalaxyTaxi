package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExtraMoneyActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_extra_money)

		val amountInput = findViewById<EditText>(R.id.amountInput)
		val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
		val okButton = findViewById<Button>(R.id.okButton)
		val backButton = findViewById<Button>(R.id.backButton)

		okButton.setOnClickListener {
			val amount = amountInput.text.toString()
			val description = descriptionInput.text.toString()

			// Validace vstupu
			if (amount.isEmpty()) {
				Toast.makeText(this, "Zadejte částku", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (description.isEmpty()) {
				Toast.makeText(this, "Zadejte popis", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			// Zápis údajů do výčetky (simulace)
			Toast.makeText(this, "+ Peníze ve výši $amount Kč byly zaznamenány", Toast.LENGTH_SHORT).show()

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
