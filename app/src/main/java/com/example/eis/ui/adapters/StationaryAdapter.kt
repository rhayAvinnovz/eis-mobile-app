package com.example.eis.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eis.R
import com.example.eis.ui.models.Stationary
import com.google.android.material.card.MaterialCardView

class StationaryAdapter(private val context: Context,
                        private val stationary: ArrayList<Stationary>,
                        private val stationaryListener: OnStationaryListener
): RecyclerView.Adapter<StationaryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val cvStationary: MaterialCardView = itemView.findViewById(R.id.cv_stationary)
        val tvCompany: TextView = itemView.findViewById(R.id.rowCompany)
        val tvYear: TextView = itemView.findViewById(R.id.rowYear)
        val tvCity: TextView = itemView.findViewById(R.id.rowCity)
        val tvStatus: TextView = itemView.findViewById(R.id.rowStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_stationary, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stationary = stationary[position]
        holder.tvCompany.text = stationary.company
        holder.tvYear.text = stationary.year
        holder.tvCity.text = stationary.city
        holder.tvStatus.text = stationary.status
        holder.cvStationary.setOnClickListener{
            stationaryListener.onStationarySelected(stationary)
        }
    }

    override fun getItemCount(): Int {
        return stationary.size
    }
}

interface OnStationaryListener{
    fun onStationarySelected(stationary: Stationary)
}