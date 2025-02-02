package com.example.galaxytaxi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RidesAdapter(private val rides: List<ShiftRide>) : RecyclerView.Adapter<RidesAdapter.RideViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ride, parent, false)
		return RideViewHolder(view)
	}

	override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
		val ride = rides[position]
		holder.startAddressText.text = ride.start
		holder.endAddressText.text = ride.end
		holder.priceText.text = "%.2f Kč".format(ride.price)
	}

	override fun getItemCount() = rides.size

	inner class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val startAddressText: TextView = itemView.findViewById(R.id.startAddressText)
		val endAddressText: TextView = itemView.findViewById(R.id.endAddressText)
		val priceText: TextView = itemView.findViewById(R.id.priceText)
	}
}
