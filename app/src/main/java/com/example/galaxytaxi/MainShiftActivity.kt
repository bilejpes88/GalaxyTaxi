package com.example.galaxytaxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainShiftActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_shift)

		findViewById<Button>(R.id.newRideButton).setOnClickListener {
			startActivity(Intent(this, NewRideActivity::class.java))
		}

		findViewById<Button>(R.id.fuelExpenseButton).setOnClickListener {
			startActivity(Intent(this, ExpensesActivity::class.java))
		}

		findViewById<Button>(R.id.personalOverviewButton).setOnClickListener {
			startActivity(Intent(this, PersonalOverviewActivity::class.java))
		}

		findViewById<Button>(R.id.extraMoneyButton).setOnClickListener {
			startActivity(Intent(this, ExtraMoneyActivity::class.java))
		}

		findViewById<Button>(R.id.endShiftButton).setOnClickListener {
			// Otevření obrazovky zadání konečných km
			startActivity(Intent(this, FinalMileageActivity::class.java))
		}
	}
}
