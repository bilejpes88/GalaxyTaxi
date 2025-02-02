package com.example.galaxytaxi

data class ShiftRide(
	val start: String = "Neznámá adresa",
	val end: String = "Neznámá adresa",
	val distance: Double = 0.0,
	val price: Double = 0.0,
	val driverName: String = "Neznámý řidič",
	val vehicle: String = "Neznámé vozidlo",
	val startTime: Long = 0L,
	val endTime: Long = 0L
)
