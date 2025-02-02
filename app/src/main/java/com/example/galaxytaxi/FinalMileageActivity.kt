package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FinalMileageActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_final_mileage)

		val mileageInput = findViewById<EditText>(R.id.mileageInput)
		val confirmEndShiftButton = findViewById<Button>(R.id.confirmEndShiftButton)

		confirmEndShiftButton.setOnClickListener {
			val finalMileage = mileageInput.text.toString().toIntOrNull()

			if (finalMileage == null) {
				Toast.makeText(this, "Zadejte platné číslo!", Toast.LENGTH_SHORT).show()
			} else {
				// Uložení dat do databáze (například pomocí funkce saveFinalMileageToDatabase())
				saveFinalMileageToDatabase(finalMileage)

				// Přechod na přihlašovací obrazovku
				val intent = Intent(this, LoginActivity::class.java)
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
				startActivity(intent)
				finish()
			}
		}
	}

	private fun saveFinalMileageToDatabase(mileage: Int) {
		// Sem implementujte logiku uložení finálního počtu kilometrů do databáze
	}
}
