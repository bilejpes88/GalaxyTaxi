package com.example.galaxytaxi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RideInProgressActivity : AppCompatActivity() {

	private lateinit var locationManager: LocationManager
	private var lastLocation: Location? = null
	private var totalDistance = 0.0
	private val pricePerKm = 30.0
	private lateinit var kmText: EditText
	private lateinit var priceText: EditText
	private lateinit var endAddressInput: AutoCompleteTextView

	private var startTime: Long = 0
	private lateinit var driverName: String
	private lateinit var selectedVehicle: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_ride_in_progress)

		kmText = findViewById(R.id.kmText)
		priceText = findViewById(R.id.priceText)
		endAddressInput = findViewById(R.id.endAddressInput)
		val getAddressButton = findViewById<Button>(R.id.getAddressButton)
		val okButton = findViewById<Button>(R.id.okButton)

		driverName = intent.getStringExtra("driverName") ?: "Neznámé"
		selectedVehicle = intent.getStringExtra("vehicle") ?: "Neznámé vozidlo"
		val startAddress = intent.getStringExtra("startAddress") ?: "Neznámá adresa"
		startTime = System.currentTimeMillis()

		findViewById<TextView>(R.id.startAddressText).text = "Start: $startAddress"

		locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
			return
		}

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5f, object : LocationListener {
			override fun onLocationChanged(location: Location) {
				if (lastLocation != null) {
					val distance = lastLocation!!.distanceTo(location) / 1000.0
					totalDistance += distance
					kmText.setText("%.1f km".format(totalDistance))
					priceText.setText("%.0f Kč".format(totalDistance * pricePerKm))
				}
				lastLocation = location
			}

			override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
			override fun onProviderEnabled(provider: String) {}
			override fun onProviderDisabled(provider: String) {}
		})

		getAddressButton.setOnClickListener {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
				val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
				if (location != null) {
					fetchAddress(location.latitude, location.longitude) { address ->
						endAddressInput.setText(address)
					}
				} else {
					endAddressInput.setText("GPS poloha není dostupná")
				}
			} else {
				Toast.makeText(this, "Nemáte povolení pro GPS", Toast.LENGTH_SHORT).show()
			}
		}

		okButton.setOnClickListener {
			val endAddress = endAddressInput.text.toString()
			val endTime = System.currentTimeMillis()

			// Uložení jízdy do databáze
			DatabaseHelper.getInstance(this).insertRide(
				startAddress, endAddress, totalDistance, totalDistance * pricePerKm,
				driverName, selectedVehicle, startTime, endTime
			)

			val intent = Intent(this, RideSummaryActivity::class.java)
			intent.putExtra("driverName", driverName)
			intent.putExtra("vehicle", selectedVehicle)
			intent.putExtra("startTime", startTime)
			intent.putExtra("endTime", endTime)
			intent.putExtra("startAddress", startAddress)
			intent.putExtra("endAddress", endAddress)
			intent.putExtra("distance", totalDistance)
			intent.putExtra("price", totalDistance * pricePerKm)
			startActivity(intent)
		}
	}

	/**
	 * Asynchronní metoda pro získání adresy pomocí GeocodeListener.
	 */
	private fun fetchAddress(latitude: Double, longitude: Double, callback: (String) -> Unit) {
		if (Geocoder.isPresent()) {
			val geocoder = Geocoder(this, Locale.getDefault())
			geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
				override fun onGeocode(addresses: List<Address>) {
					if (addresses.isNotEmpty()) {
						callback(addresses[0].getAddressLine(0))
					} else {
						callback("Adresa nebyla nalezena")
					}
				}

				override fun onError(errorMessage: String?) {
					callback(errorMessage ?: "Chyba při získávání adresy")
				}
			})
		} else {
			callback("Geokódování není dostupné")
		}
	}
}
