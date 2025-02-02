package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		// Zobrazení aktuálního data a času
		val dateText = findViewById<TextView>(R.id.currentDateText)
		val timeText = findViewById<TextView>(R.id.currentTimeText)
		val currentDateTime = getCurrentDateTime()
		dateText.text = currentDateTime.first
		timeText.text = currentDateTime.second

		// Inicializace komponent
		val userSpinner = findViewById<Spinner>(R.id.userSpinner)
		val passwordInput = findViewById<EditText>(R.id.passwordInput)
		val okButton = findViewById<Button>(R.id.okButton)

		// Simulovaná jména řidičů
		val users = arrayOf("Řidič 1", "Řidič 2", "Admin", "Majitel Renata")
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		userSpinner.adapter = adapter

		// Akce tlačítka OK
		okButton.setOnClickListener {
			val selectedUser = userSpinner.selectedItem.toString()
			val enteredPassword = passwordInput.text.toString()

			if (validateLogin(selectedUser, enteredPassword)) {
				Toast.makeText(this, "Přihlášení úspěšné", Toast.LENGTH_SHORT).show()
				val intent = Intent(this, VehicleSelectionActivity::class.java)
				startActivity(intent)
				finish()
			} else {
				Toast.makeText(this, "Nesprávné heslo", Toast.LENGTH_SHORT).show()
			}
		}
	}

	// Funkce pro získání aktuálního data a času
	private fun getCurrentDateTime(): Pair<String, String> {
		val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
		val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

		// Nastavení časového pásma na Středoevropský čas
		dateFormat.timeZone = TimeZone.getTimeZone("Europe/Prague")
		timeFormat.timeZone = TimeZone.getTimeZone("Europe/Prague")

		val date = Date()
		return Pair(dateFormat.format(date), timeFormat.format(date))
	}

	// Validace přihlášení (prozatím jednoduchá)
	private fun validateLogin(username: String, password: String): Boolean {
		return when (username) {
			"Řidič 1" -> password == "heslo1"
			"Řidič 2" -> password == "heslo2"
			"Admin" -> password == "admin123"
			"Majitel Renata" -> password == "renata2024"
			else -> false
		}
	}
}
