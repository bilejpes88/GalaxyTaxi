package com.example.galaxytaxi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.*
import java.util.*

class NewRideActivity : AppCompatActivity() {

	private lateinit var locationManager: LocationManager
	private lateinit var addressInput: AutoCompleteTextView
	private lateinit var addressSuggestions: ArrayAdapter<String>
	private val savedAddresses = mutableListOf<String>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_ride)

		// Inicializace komponent
		val addressButton = findViewById<Button>(R.id.startButton)
		addressInput = findViewById(R.id.addressInput)
		val okButton = findViewById<Button>(R.id.okButton)
		val cancelButton = findViewById<Button>(R.id.cancelButton)

		// Adapter pro ukládání adres
		addressSuggestions = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, savedAddresses)
		addressInput.setAdapter(addressSuggestions)

		// Inicializace LocationManageru
		locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

		// Nastavení akce pro tlačítko "Adresa"
		addressButton.setOnClickListener {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
				return@setOnClickListener
			}

			// Získání aktuální polohy
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5f, object : LocationListener {
				override fun onLocationChanged(location: Location) {
					locationManager.removeUpdates(this)
					getAddressFromLocation(location.latitude, location.longitude)
				}

				override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
				override fun onProviderEnabled(provider: String) {}
				override fun onProviderDisabled(provider: String) {}
			})
		}

		// Akce při stisknutí tlačítka OK
		okButton.setOnClickListener {
			val enteredAddress = addressInput.text.toString()
			if (enteredAddress.isNotEmpty() && !savedAddresses.contains(enteredAddress)) {
				savedAddresses.add(enteredAddress)
				addressSuggestions.notifyDataSetChanged()
			}

			// Přechod na obrazovku RideInProgressActivity
			val intent = Intent(this, RideInProgressActivity::class.java)
			intent.putExtra("startAddress", enteredAddress)
			startActivity(intent)
		}

		// Akce při stisknutí tlačítka Zrušit
		cancelButton.setOnClickListener {
			// Přesun na obrazovku MainShiftActivity
			val intent = Intent(this, MainShiftActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			finish()
		}
	}

	@Suppress("DEPRECATION")
	@OptIn(DelicateCoroutinesApi::class)
	private fun getAddressFromLocation(latitude: Double, longitude: Double) {
		GlobalScope.launch(Dispatchers.IO) {
			val geocoder = Geocoder(this@NewRideActivity, Locale.getDefault())
			try {
				val addresses = geocoder.getFromLocation(latitude, longitude, 1)
				if (!addresses.isNullOrEmpty()) {
					val address = addresses[0].getAddressLine(0)
					withContext(Dispatchers.Main) {
						addressInput.setText(address)
					}
				} else {
					withContext(Dispatchers.Main) {
						Toast.makeText(this@NewRideActivity, "Nepodařilo se získat adresu", Toast.LENGTH_SHORT).show()
					}
				}
			} catch (e: Exception) {
				withContext(Dispatchers.Main) {
					Toast.makeText(this@NewRideActivity, "Chyba při získávání adresy", Toast.LENGTH_SHORT).show()
				}
				e.printStackTrace()
			}
		}
	}
}
