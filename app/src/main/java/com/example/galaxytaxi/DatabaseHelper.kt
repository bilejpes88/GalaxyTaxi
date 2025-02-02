package com.example.galaxytaxi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(
			"""CREATE TABLE rides (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                startAddress TEXT NOT NULL,
                endAddress TEXT NOT NULL,
                distance REAL NOT NULL,
                price REAL NOT NULL,
                driverName TEXT NOT NULL,
                vehicle TEXT NOT NULL,
                startTime INTEGER NOT NULL,
                endTime INTEGER NOT NULL
            )"""
		)

		db.execSQL(
			"""CREATE TABLE expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                description TEXT NOT NULL,
                amount REAL NOT NULL
            )"""
		)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL("DROP TABLE IF EXISTS rides")
		db.execSQL("DROP TABLE IF EXISTS expenses")
		onCreate(db)
	}

	fun insertRide(startAddress: String, endAddress: String, distance: Double, price: Double, driverName: String, vehicle: String, startTime: Long, endTime: Long) {
		val db = writableDatabase
		val values = ContentValues().apply {
			put("startAddress", startAddress)
			put("endAddress", endAddress)
			put("distance", distance)
			put("price", price)
			put("driverName", driverName)
			put("vehicle", vehicle)
			put("startTime", startTime)
			put("endTime", endTime)
		}
		db.insert("rides", null, values)
		db.close()
	}

	fun getAllRides(): List<ShiftRide> {
		val rides = mutableListOf<ShiftRide>()
		val db = readableDatabase
		val cursor = db.rawQuery("SELECT startAddress, endAddress, distance, price, driverName, vehicle, startTime, endTime FROM rides", null)

		if (cursor.moveToFirst()) {
			do {
				val ride = ShiftRide(
					start = cursor.getString(0),
					end = cursor.getString(1),
					distance = cursor.getDouble(2),
					price = cursor.getDouble(3),
					driverName = cursor.getString(4),
					vehicle = cursor.getString(5),
					startTime = cursor.getLong(6),
					endTime = cursor.getLong(7)
				)
				rides.add(ride)
			} while (cursor.moveToNext())
		}
		cursor.close()
		db.close()
		return rides
	}

	fun getAllExpenses(): List<Expense> {
		val expenses = mutableListOf<Expense>()
		val db = readableDatabase
		val cursor = db.rawQuery("SELECT description, amount FROM expenses", null)

		if (cursor.moveToFirst()) {
			do {
				val expense = Expense(
					description = cursor.getString(0),
					amount = cursor.getDouble(1)
				)
				expenses.add(expense)
			} while (cursor.moveToNext())
		}
		cursor.close()
		db.close()
		return expenses
	}

	companion object {
		private const val DATABASE_NAME = "galaxytaxi.db"
		private const val DATABASE_VERSION = 1

		@Volatile
		private var instance: DatabaseHelper? = null

		fun getInstance(context: Context): DatabaseHelper {
			return instance ?: synchronized(this) {
				instance ?: DatabaseHelper(context.applicationContext).also { instance = it }
			}
		}
	}
}
