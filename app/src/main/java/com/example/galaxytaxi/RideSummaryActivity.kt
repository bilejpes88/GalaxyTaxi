package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RideSummaryActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_ride_summary)

		// Inicializace komponent
		val kmEdit = findViewById<EditText>(R.id.kmEdit)
		val priceEdit = findViewById<EditText>(R.id.priceEdit)
		val newRideButton = findViewById<Button>(R.id.newRideButton)
		val mainMenuButton = findViewById<Button>(R.id.mainMenuButton)
		val okButton = findViewById<Button>(R.id.okButton)

		// Načtení dat z předchozí aktivity
		val driverName = intent.getStringExtra("driverName") ?: "Neznámý řidič"
		val vehicle = intent.getStringExtra("vehicle") ?: "Neznámé vozidlo"
		val startTime = intent.getLongExtra("startTime", 0)
		val endTime = intent.getLongExtra("endTime", 0)
		val startAddress = intent.getStringExtra("startAddress") ?: "Neznámá adresa"
		val endAddress = intent.getStringExtra("endAddress") ?: "Neznámá adresa"
		val distance = intent.getDoubleExtra("distance", 0.0)
		val price = intent.getDoubleExtra("price", 0.0)

		// Formátování času
		val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
		val formattedStartTime = timeFormatter.format(Date(startTime))
		val formattedEndTime = timeFormatter.format(Date(endTime))

		// Zobrazení adres a času
		findViewById<TextView>(R.id.startAddressTimeText).text = "Start: $startAddress v $formattedStartTime"
		findViewById<TextView>(R.id.endAddressTimeText).text = "Konec: $endAddress v $formattedEndTime"

		// Zobrazení km a ceny, pole jsou editovatelná
		kmEdit.setText("%.1f".format(distance))
		priceEdit.setText("%.0f".format(price))

		// Akce pro tlačítko OK
		okButton.setOnClickListener {
			// Načtení aktuálních hodnot z polí
			val updatedKm = kmEdit.text.toString().toDoubleOrNull() ?: distance
			val updatedPrice = priceEdit.text.toString().toDoubleOrNull() ?: price

			// Přechod na obrazovku DataDoPrehleduActivity
			val intent = Intent(this, DataDoPrehleduActivity::class.java)
			intent.putExtra("driverName", driverName)
			intent.putExtra("vehicle", vehicle)
			intent.putExtra("startTime", formattedStartTime)
			intent.putExtra("endTime", formattedEndTime)
			intent.putExtra("startAddress", startAddress)
			intent.putExtra("endAddress", endAddress)
			intent.putExtra("distance", updatedKm)
			intent.putExtra("price", updatedPrice)
			startActivity(intent)
		}

		// Akce pro tlačítko "Další zakázka"
		newRideButton.setOnClickListener {
			startActivity(Intent(this, NewRideActivity::class.java))
			finish()
		}

		// Akce pro tlačítko "Hlavní menu"
		mainMenuButton.setOnClickListener {
			val intent = Intent(this, MainShiftActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			finish()
		}
	}
}
