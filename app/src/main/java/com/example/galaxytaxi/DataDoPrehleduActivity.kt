package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DataDoPrehleduActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_data_do_prehledu)

		val driverNameText = findViewById<TextView>(R.id.driverNameText)
		val startTimeText = findViewById<TextView>(R.id.startTimeText)
		val startAddressText = findViewById<TextView>(R.id.startAddressText)
		val endTimeText = findViewById<TextView>(R.id.endTimeText)
		val endAddressText = findViewById<TextView>(R.id.endAddressText)
		val distanceText = findViewById<TextView>(R.id.distanceText)
		val priceText = findViewById<TextView>(R.id.priceText)
		val vehicleText = findViewById<TextView>(R.id.vehicleText)
		val dateText = findViewById<TextView>(R.id.dateText)
		val endButton = findViewById<Button>(R.id.endButton)

		val driverName = intent.getStringExtra("driverName") ?: "Neznámé"
		val startTime = intent.getLongExtra("startTime", 0L)
		val endTime = intent.getLongExtra("endTime", 0L)
		val startAddress = intent.getStringExtra("startAddress") ?: "Neznámé místo"
		val endAddress = intent.getStringExtra("endAddress") ?: "Neznámé místo"
		val distance = intent.getDoubleExtra("distance", 0.0)
		val price = intent.getDoubleExtra("price", 0.0)
		val vehicle = intent.getStringExtra("vehicle") ?: "Neznámé vozidlo"

		val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
		val formattedStartTime = dateFormat.format(Date(startTime))
		val formattedEndTime = dateFormat.format(Date(endTime))
		val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

		driverNameText.text = driverName
		startTimeText.text = formattedStartTime
		startAddressText.text = startAddress
		endTimeText.text = formattedEndTime
		endAddressText.text = endAddress
		distanceText.text = "%.1f km".format(distance)
		priceText.text = "%.0f Kč".format(price)
		vehicleText.text = vehicle
		dateText.text = formattedDate

		endButton.setOnClickListener {
			val intent = Intent(this, MainShiftActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			finish()
		}
	}
}
