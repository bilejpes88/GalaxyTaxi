package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VehicleSelectionActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_vehicle_selection)

		val vehicleSpinner = findViewById<Spinner>(R.id.vehicleSpinner)
		val startingMileageInput = findViewById<EditText>(R.id.startingMileageInput)
		val startShiftButton = findViewById<Button>(R.id.startShiftButton)

		// Přiřazení dat ke spinneru
		val vehicles = arrayOf("Auto 1", "Auto 2", "Auto 3")
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, vehicles)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		vehicleSpinner.adapter = adapter

		startShiftButton.setOnClickListener {
			val selectedVehicle = vehicleSpinner.selectedItem?.toString() ?: ""

			if (selectedVehicle.isEmpty()) {
				Toast.makeText(this, "Vyberte vozidlo", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val startingMileage = startingMileageInput.text.toString()

			if (startingMileage.isEmpty()) {
				Toast.makeText(this, "Zadejte počáteční kilometry", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			// Přejdi na další obrazovku
			val intent = Intent(this, MainShiftActivity::class.java)
			startActivity(intent)
			finish()
		}
	}
}
